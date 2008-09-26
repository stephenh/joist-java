package org.exigencecorp.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.exigencecorp.util.Log;
import org.exigencecorp.util.ToString;

public class Jdbc {

    private Jdbc() {
    }

    public static int queryForInt(Connection connection, String sql, Object... args) {
        int value = -1;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            sql = ToString.interpolate(sql, args);
            Log.trace("sql = {}", sql);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(rs, stmt);
        }
        return value;
    }

    public static int queryForInt(DataSource ds, String sql, Object... args) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            return Jdbc.queryForInt(connection, sql, args);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(connection);
        }
    }

    public static int executeUpdate(Connection connection, String sql, Object... args) {
        Statement stmt = null;
        try {
            sql = ToString.interpolate(sql, args);
            Log.trace("sql = {}", sql);
            stmt = connection.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(stmt);
        }
    }

    public static int executeUpdate(DataSource ds, String sql, Object... args) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            return Jdbc.executeUpdate(connection, sql, args);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(connection);
        }
    }

    public static void query(DataSource ds, String sql, RowMapper rse) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            Jdbc.query(connection, sql, rse);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(connection);
        }
    }

    public static void query(Connection connection, String sql, RowMapper rse) {
        Statement s = null;
        ResultSet rs = null;
        try {
            Log.trace("sql = {}", sql);
            s = connection.createStatement();
            rs = s.executeQuery(sql);
            while (rs.next()) {
                rse.mapRow(rs);
            }
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(rs, s);
        }
    }

    public static void query(Connection connection, String sql, List<Object> parameters, RowMapper rse) {
        PreparedStatement s = null;
        ResultSet rs = null;
        try {
            Log.trace("sql = {}", sql);
            Log.trace("parameters = {}", parameters);
            s = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                s.setObject(i + 1, parameters.get(i));
            }
            rs = s.executeQuery();
            while (rs.next()) {
                rse.mapRow(rs);
            }
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(rs, s);
        }
    }

    public static void update(DataSource ds, String sql, UpdateMapper mapper) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            Jdbc.update(connection, sql, mapper);
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(connection);
        }
    }

    public static void update(Connection connection, String sql, UpdateMapper mapper) {
        PreparedStatement ps = null;
        try {
            Log.trace("sql = {}", sql);
            ps = connection.prepareStatement(sql);
            mapper.mapUpdate(ps);
            ps.executeUpdate();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(ps);
        }
    }

    public static void update(Connection connection, String sql, List<Object> parameters) {
        PreparedStatement ps = null;
        try {
            Log.trace("sql = {}", sql);
            Log.trace("parameters = {}", parameters);
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            ps.executeUpdate();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        } finally {
            Jdbc.closeSafely(ps);
        }
    }

    public static void closeSafely(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                continue;
            }
            try {
                if (o instanceof ResultSet) {
                    ((ResultSet) o).close();
                } else if (o instanceof Statement) {
                    ((Statement) o).close();
                } else if (o instanceof PreparedStatement) {
                    ((PreparedStatement) o).close();
                } else if (o instanceof Connection) {
                    ((Connection) o).close();
                } else {
                    throw new RuntimeException("Unhandled object " + o);
                }
            } catch (SQLException se) {
                // Suppress
            }
        }
    }

}
