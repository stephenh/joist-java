package org.exigencecorp.domainobjects.codegen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
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
        + " tc.constraint_type, kcu.table_name, kcu.column_name, kcu.constraint_name, ccu.table_name AS ref_table_name, ccu.column_name AS ref_column_name"
        + " FROM information_schema.key_column_usage kcu"
        + " INNER JOIN information_schema.tables t ON kcu.table_name = t.table_name"
        + " INNER JOIN information_schema.constraint_column_usage ccu ON kcu.constraint_name = ccu.constraint_name"
        + " INNER JOIN information_schema.table_constraints tc ON kcu.constraint_name = tc.constraint_name"
        + " WHERE t.table_schema = 'public'"
        + " AND tc.constraint_type != 'UNIQUE'";

    private final DataSource dataSource;
    private final List<InformationSchemaColumn> columns = new ArrayList<InformationSchemaColumn>();

    public InformationSchemaWrapper(DataSource dataSource) {
        this.dataSource = dataSource;
        this.findColumns();
        this.findConstraints();
    }

    public List<InformationSchemaColumn> getColumnMetaData() {
        return this.columns;
    }

    public boolean isCodeTable(String tableName) {
        List<String> actualColumns = this.getColumnNames(tableName);
        List<String> neededColumns = Copy.list("id", "code", "name", "version");
        return CollectionUtils.isEqualCollection(actualColumns, neededColumns);
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
    }

    private void findConstraints() {
        Jdbc.query(this.dataSource, InformationSchemaWrapper.constraintSql, new RowMapper() {
            public void mapRow(ResultSet rs) throws SQLException {
                InformationSchemaColumn column = InformationSchemaWrapper.this.getColumn(rs.getString("table_name"), rs.getString("column_name"));
                String constraintType = rs.getString("constraint_type");
                if ("PRIMARY KEY".equals(constraintType)) {
                    column.primaryKey = true;
                } else if ("FOREIGN KEY".equals(constraintType)) {
                    column.foreignKeyConstraintName = rs.getString("constraint_name");
                    column.foreignKeyTableName = rs.getString("ref_table_name");
                    column.foreignKeyColumnName = rs.getString("ref_column_name");
                } else {
                    throw new RuntimeException("Unknown constraint type " + constraintType);
                }
            }
        });
    }
}
