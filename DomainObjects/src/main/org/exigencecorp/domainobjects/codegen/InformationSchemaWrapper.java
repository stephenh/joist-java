package org.exigencecorp.domainobjects.codegen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.jdbc.RowMapper;
import org.exigencecorp.util.Copy;

public class InformationSchemaWrapper {

    private static final String columnsSql = "SELECT"
        + " c.table_name, c.column_name, c.data_type, c.character_maximum_length, c.is_nullable, c.column_default as default_value"
        + " FROM information_schema.columns c"
        + " INNER JOIN information_schema.tables t on c.table_name = t.table_name"
        + " WHERE t.table_schema = 'public'";
    private static final String constraintSql = "SELECT"
        + " kcu.table_name, kcu.column_name, kcu.constraint_name, ccu.table_name AS ref_table_name, ccu.column_name AS ref_column_name"
        + " FROM information_schema.key_column_usage kcu"
        + " INNER JOIN information_schema.constraint_column_usage ccu ON kcu.constraint_name = ccu.constraint_name"
        + " WHERE kcu.table_schema = 'public'";
    // For some reason pg is a dog if we join table_constraints into the above query, so do it separately
    // Ugly but SchemaCheckTest went from 5.5s to 1.6s with aoviding this join
    private static final String constraintTypeSql = "SELECT"
        + " tc.constraint_name, tc.constraint_type"
        + " FROM information_schema.table_constraints tc";

    private final DataSource dataSource;
    private final List<InformationSchemaColumn> columns = new ArrayList<InformationSchemaColumn>();

    public InformationSchemaWrapper(DataSource dataSource) {
        this.dataSource = dataSource;
        this.findColumns();
        this.findConstraints();
    }

    public List<InformationSchemaColumn> getColumns() {
        return this.columns;
    }

    public List<String> getCodeTables() {
        List<String> tables = new ArrayList<String>();
        for (InformationSchemaColumn column : this.columns) {
            if (!tables.contains(column.tableName) && this.isCodeTable(column.tableName)) {
                tables.add(column.tableName);
            }
        }
        return tables;
    }

    public List<String> getManyToManyTables() {
        List<String> tables = new ArrayList<String>();
        for (InformationSchemaColumn column : this.columns) {
            if (!tables.contains(column.tableName) && this.isManyToManyTable(column.tableName)) {
                tables.add(column.tableName);
            }
        }
        return tables;
    }

    public int getSchemaHashCode() {
        StringBuilder sb = new StringBuilder();
        for (InformationSchemaColumn column : this.columns) {
            sb.append(column.toString());
        }
        return sb.toString().hashCode();
    }

    private boolean isCodeTable(String tableName) {
        List<String> actualColumns = this.getColumnNames(tableName);
        if (actualColumns.size() != 4) {
            return false;
        }
        actualColumns.removeAll(Copy.list("id", "code", "name", "version"));
        return actualColumns.size() == 0;
    }

    private boolean isManyToManyTable(String tableName) {
        if (!tableName.contains("_to_")) {
            return false;
        }
        List<String> actualColumns = this.getColumnNames(tableName);
        if (actualColumns.size() != 4) {
            return false;
        }
        actualColumns.remove("id");
        actualColumns.remove("version");
        return actualColumns.size() == 2 && actualColumns.get(0).endsWith("_id") && actualColumns.get(1).endsWith("_id");
    }

    private List<String> getColumnNames(String tableName) {
        List<String> columns = new ArrayList<String>();
        for (InformationSchemaColumn column : this.columns) {
            if (column.tableName.equals(tableName)) {
                columns.add(column.name);
            }
        }
        return columns;
    }

    private InformationSchemaColumn getColumn(String tableName, String columnName) {
        for (InformationSchemaColumn column : this.columns) {
            if (column.tableName.equals(tableName) && column.name.equals(columnName)) {
                return column;
            }
        }
        throw new RuntimeException("Column not found for " + tableName + "." + columnName);
    }

    private void findColumns() {
        Jdbc.query(this.dataSource, InformationSchemaWrapper.columnsSql, new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                InformationSchemaColumn column = new InformationSchemaColumn();
                column.tableName = rs.getString("table_name");
                column.name = rs.getString("column_name");
                column.dataType = rs.getString("data_type");
                column.maximumLength = rs.getInt("character_maximum_length");
                column.nullable = rs.getString("is_nullable").equals("YES");
                column.defaultValue = rs.getString("default_value");
                InformationSchemaWrapper.this.columns.add(column);
            }
        });
        Collections.sort(this.columns);
    }

    private void findConstraints() {
        final Map<String, String> constraintNameToType = new HashMap<String, String>();
        Jdbc.query(this.dataSource, InformationSchemaWrapper.constraintTypeSql, new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                constraintNameToType.put(rs.getString(1), rs.getString(2));
            }
        });

        Jdbc.query(this.dataSource, InformationSchemaWrapper.constraintSql, new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                InformationSchemaColumn column = InformationSchemaWrapper.this.getColumn(rs.getString("table_name"), rs.getString("column_name"));
                String constraintType = constraintNameToType.get(rs.getString("constraint_name"));
                if ("PRIMARY KEY".equals(constraintType)) {
                    column.primaryKey = true;
                } else if ("FOREIGN KEY".equals(constraintType)) {
                    column.foreignKeyConstraintName = rs.getString("constraint_name");
                    column.foreignKeyTableName = rs.getString("ref_table_name");
                    column.foreignKeyColumnName = rs.getString("ref_column_name");
                } else if ("UNIQUE".equals(constraintType)) {
                    // pass
                } else {
                    throw new RuntimeException("Unknown constraint type " + constraintType);
                }
            }
        });
    }
}
