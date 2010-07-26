package joist.migrations.fill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import joist.jdbc.Jdbc;

public abstract class ResultSetFillInStrategy implements FillInStrategy {

    protected abstract void fillIn(ResultSet rs, FillInValues values) throws SQLException;

    public final void fillIn(Connection connection, String tableName, String columnName) throws SQLException {
        FillInValues values = new FillInValues();

        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Get the fragments
            stmt = connection.createStatement();
            stmt.setFetchSize(100);
            stmt.setFetchDirection(ResultSet.FETCH_FORWARD);
            rs = stmt.executeQuery("select * from \"" + tableName + "\"");
            this.fillIn(rs, values);

            // Update the fragments
            for (Map.Entry<Integer, String> entry : values.getValues().entrySet()) {
                Statement s = connection.createStatement();
                s.executeUpdate("UPDATE \"" + tableName + "\" SET \"" + columnName + "\" = " + entry.getValue() + " WHERE id = " + entry.getKey());
                s.close();
            }
        } finally {
            Jdbc.closeSafely(rs, stmt);
        }
    }

    public static class FillInValues {
        private Map<Integer, String> values = new LinkedHashMap<Integer, String>();

        public void add(int id, String fragment) {
            this.values.put(id, fragment);
        }

        public Map<Integer, String> getValues() {
            return this.values;
        }
    }

}
