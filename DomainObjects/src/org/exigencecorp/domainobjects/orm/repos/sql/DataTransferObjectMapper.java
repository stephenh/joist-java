package org.exigencecorp.domainobjects.orm.repos.sql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.jdbc.RowMapper;

public class DataTransferObjectMapper<T extends DomainObject, R> implements RowMapper {

    private final Alias<T> from;
    private final Class<R> rowType;
    private final List<R> results;

    public DataTransferObjectMapper(Alias<T> from, Class<R> rowType, List<R> results) {
        this.from = from;
        this.rowType = rowType;
        this.results = results;
    }

    public void mapRow(ResultSet rs) throws SQLException {
        R row = this.newInstance();
        for (Field field : this.rowType.getFields()) {
            try {
                field.set(row, rs.getObject(field.getName()));
            } catch (IllegalAccessException iae) {
            }
        }
        this.results.add(row);
    }

    private R newInstance() {
        try {
            return this.rowType.newInstance();
        } catch (IllegalAccessException iae) {
            throw new RuntimeException(iae);
        } catch (InstantiationException ie) {
            throw new RuntimeException(ie);
        }
    }

}
