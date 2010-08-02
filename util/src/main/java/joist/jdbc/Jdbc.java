package joist.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import joist.util.Interpolate;
import joist.util.Log;

public class Jdbc {

    private Jdbc() {
    }

    public static int queryForInt(Connection connection, String sql, Object... args) {
        int value = -1;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            sql = Interpolate.string(sql, args);
            Log.trace("sql = {}", sql);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                value = rs.getInt(1);
            }
        } catch (SQLException se) {
            throw new JdbcException(se);
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
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(connection);
        }
    }

    public static Object[] queryForRow(Connection connection, String sql, Object... args) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            sql = Interpolate.string(sql, args);
            Log.trace("sql = {}", sql);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            int count = rs.getMetaData().getColumnCount();
            Object[] objects = new Object[count];
            if (rs.next()) {
                for (int i = 0; i < count; i++) {
                    objects[i] = rs.getObject(i + 1);
                }
            }
            return objects;
        } catch (SQLException se) {
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(rs, stmt);
        }
    }

    public static Object[] queryForRow(DataSource ds, String sql, Object... args) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            return Jdbc.queryForRow(connection, sql, args);
        } catch (SQLException se) {
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(connection);
        }
    }

    public static int update(Connection connection, String sql, Object... args) {
        Statement stmt = null;
        try {
            sql = Interpolate.string(sql, args);
            Log.trace("sql = {}", sql);
            stmt = connection.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException se) {
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(stmt);
        }
    }

    public static int update(DataSource ds, String sql, Object... args) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            return Jdbc.update(connection, sql, args);
        } catch (SQLException se) {
            throw new JdbcException(se);
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
            throw new JdbcException(se);
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
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(rs, s);
        }
    }

    public static void query(DataSource ds, String sql, List<Object> parameters, RowMapper rse) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            Jdbc.query(connection, sql, parameters, rse);
        } catch (SQLException se) {
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(connection);
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
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(rs, s);
        }
    }

    public static int update(Connection connection, String sql, List<Object> parameters) {
        PreparedStatement ps = null;
        try {
            Log.trace("sql = {}", sql);
            Log.trace("parameters = {}", parameters);
            ps = connection.prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
            return ps.executeUpdate();
        } catch (SQLException se) {
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(ps);
        }
    }

    public static Integer[] insertBatch(Connection connection, String sql, List<List<Object>> allParameters) {
        PreparedStatement ps = null;
        try {
            Log.trace("sql = {}", sql);
            ps = connection.prepareStatement(sql, new String[] { "id" });
            for (List<Object> parameters : allParameters) {
                Log.trace("parameters = {}", parameters);
                for (int i = 0; i < parameters.size(); i++) {
                    ps.setObject(i + 1, parameters.get(i));
                }
                ps.addBatch();
            }
            ps.executeBatch();

            Integer[] keys = new Integer[allParameters.size()];
            ResultSet ks = ps.getGeneratedKeys();
            int i = 0;
            while (ks.next()) {
                keys[i++] = ks.getInt(1);
            }
            ks.close();
            return keys;
        } catch (SQLException se) {
            // note that MySQL's BatchUpdateException.getNextException is null and so unreliable
            throw new JdbcException(Jdbc.nextUntilNotNull(se));
        } finally {
            Jdbc.closeSafely(ps);
        }
    }

    public static int update(DataSource ds, String sql, List<Object> parameters) {
        Connection connection = null;
        try {
            connection = ds.getConnection();
            return Jdbc.update(connection, sql, parameters);
        } catch (SQLException se) {
            throw new JdbcException(se);
        } finally {
            Jdbc.closeSafely(connection);
        }
    }

    public static List<Integer> updateBatch(Connection connection, String sql, List<List<Object>> allParameters) {
        List<Integer> changed = new ArrayList<Integer>();
        PreparedStatement ps = null;
        try {
            Log.trace("sql = {}", sql);
            ps = connection.prepareStatement(sql);
            for (List<Object> parameters : allParameters) {
                Log.trace("parameters = {}", parameters);
                for (int i = 0; i < parameters.size(); i++) {
                    ps.setObject(i + 1, parameters.get(i));
                }
                ps.addBatch();
            }
            int[] is = ps.executeBatch();
            for (int i : is) {
                changed.add(i);
            }
            return changed;
        } catch (SQLException se) {
            // note that MySQL's BatchUpdateException.getNextException is null and so unreliable
            throw new JdbcException(Jdbc.nextUntilNotNull(se));
        } finally {
            Jdbc.closeSafely(ps);
        }
    }

    public static SQLException nextUntilNotNull(SQLException current) {
        while (current.getNextException() != null) {
            current = current.getNextException();
        }
        return current;
    }

    public static void closeSafely(Object... objects) {
        for (Object o : objects) {
            try {
                if (o instanceof ResultSet) {
                    ((ResultSet) o).close();
                } else if (o instanceof Statement) {
                    ((Statement) o).close();
                } else if (o instanceof PreparedStatement) {
                    ((PreparedStatement) o).close();
                } else if (o instanceof Connection) {
                    ((Connection) o).close();
                } else if (o != null) {
                    throw new RuntimeException("Unhandled object " + o);
                }
            } catch (Exception e) {
                Log.warn("Error occurred closing {}", e, o);
            }
        }
    }

}
