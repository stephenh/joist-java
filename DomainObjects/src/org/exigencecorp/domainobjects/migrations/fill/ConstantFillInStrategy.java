package org.exigencecorp.domainobjects.migrations.fill;

import java.sql.Connection;
import java.sql.SQLException;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Wrap;

public class ConstantFillInStrategy implements FillInStrategy {

    private String fragment;

    public ConstantFillInStrategy(String fragment) {
        this.fragment = fragment;
    }

    public ConstantFillInStrategy(int i) {
        this.fragment = String.valueOf(i);
    }

    public void fillIn(Connection connection, String tableName, String columnName) throws SQLException {
        Jdbc.executeUpdate(connection, "update {} set {} = {}", Wrap.quotes(tableName), columnName, this.fragment);
    }

}
