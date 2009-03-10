package joist.domain.migrations;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import joist.domain.Code;
import joist.domain.codegen.InformationSchemaWrapper;


import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.jdbc.RowMapper;
import org.exigencecorp.util.Inflector;
import org.exigencecorp.util.Interpolate;
import org.exigencecorp.util.Reflection;

public class SchemaCheck {

    private final String packageName;
    private final DataSource dataSource;
    private final InformationSchemaWrapper wrapper;

    /**
     * @param dataSource a non-sa datasource, as we want to SchemaCheck in production, which doesn't have sa access
     */
    public SchemaCheck(String packageName, DataSource dataSource) {
        this.packageName = packageName;
        this.dataSource = dataSource;
        this.wrapper = new InformationSchemaWrapper(dataSource);
    }

    public void checkStructureMatch(int code) {
        if (code != this.wrapper.getSchemaHashCode()) {
            throw new RuntimeException("Database hash did not match the codebase's generated hash");
        }
    }

    public void checkCodesMatch() {
        for (final String tableName : this.wrapper.getCodeTables()) {
            int maxId = -1;
            final Code[] codes = this.getCodes(Inflector.camelize(tableName));

            // Look for Java codes not matching database codes first
            for (Code code : codes) {
                // Keep track of our largest code
                maxId = Math.max(maxId, code.getId());

                // Try an exact match first
                int exactMatch = Jdbc.queryForInt(this.dataSource, "select count(*) from \"{}\" where id = {} and code = '{}'",//
                    tableName,
                    code.getId(),
                    code.getCode());
                if (exactMatch == 1) {
                    continue; // This one is okay, continue
                }

                // Next see if we're getting id collision
                int idMatch = Jdbc.queryForInt(this.dataSource, "select count(*) from \"{}\" where id = {}", tableName, code.getId());
                if (idMatch == 0) {
                    String message = Interpolate.string("Code {} {}-{} is not in the database", tableName, code.getId(), code.getCode());
                    throw new RuntimeException(message);
                } else if (idMatch == 1) {
                    String message = Interpolate.string("Code {} {}-{}'s id is taken by a different code", tableName, code.getId(), code.getCode());
                    throw new RuntimeException(message);
                }
            }

            // Now watch for database codes not matching Java codes
            Jdbc.query(this.dataSource, "select * from \"" + tableName + "\"", new RowMapper() {
                public void mapRow(ResultSet rs) throws SQLException {
                    // Make sure this id is in our Java codes
                    int id = rs.getInt("id");
                    boolean found = false;
                    for (Code code : codes) {
                        if (code.getId() == id) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        String message = Interpolate.string("Database code {} {} is not in the codebase", tableName, id);
                        throw new RuntimeException(message);
                    }
                }
            });

            // Now check the sequence value
            int lastValue = Jdbc.queryForInt(this.dataSource, "select next_id - 1 from code_id where table_name = '{}'", tableName);
            if (maxId > lastValue) {
                String message = Interpolate.string("Code {} has a max id of {} but the last assigned was {}", tableName, maxId, lastValue);
                throw new RuntimeException(message);
            }
        }
    }

    private Code[] getCodes(String codeClassName) {
        try {
            Class<?> codeClass = Class.forName(this.packageName + "." + codeClassName);
            Method valuesMethod = codeClass.getMethod("values", new Class[] {});
            return (Code[]) Reflection.invoke(valuesMethod, null, new Object[] {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
