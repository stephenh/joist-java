package joist.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import joist.util.Interpolate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Jdbc {

  private static final boolean trackStats = Boolean.valueOf(System.getProperty("joist.util.jdbc.trackStats", "false"));
  private static final AtomicInteger queries = new AtomicInteger(0);
  private static final AtomicInteger updates = new AtomicInteger(0);

  private Jdbc() {
  }

  /** @return the number of queries, only if {@link #trackStats} is enabled. */
  public static int numberOfQueries() {
    return queries.get();
  }

  /** @return the number of updates, only if {@link #trackStats} is enabled. */
  public static int numberOfUpdates() {
    return updates.get();
  }

  /** Resets the stats (only meaningful if {@link #trackStats} is enabled. */
  public static void resetStats() {
    updates.set(0);
    queries.set(0);
  }

  public static int queryForInt(Connection connection, String sql, Object... args) {
    int value = -1;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      sql = Interpolate.string(sql, args);
      log.trace("sql = {}", sql);
      tickQueriesIfTracking();
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
      log.trace("sql = {}", sql);
      tickQueriesIfTracking();
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
      log.trace("sql = {}", sql);
      tickUpdatesIfTracking();
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
      log.trace("sql = {}", sql);
      tickQueriesIfTracking();
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
      log.trace("sql = {}", sql);
      log.trace("parameters = {}", parameters);
      tickQueriesIfTracking();
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
      log.trace("sql = {}", sql);
      log.trace("parameters = {}", parameters);
      tickUpdatesIfTracking();
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

  public static Long[] insertBatch(Connection connection, String sql, List<List<Object>> allParameters) {
    PreparedStatement ps = null;
    try {
      log.trace("sql = {}", sql);
      tickUpdatesIfTracking();
      ps = connection.prepareStatement(sql, new String[] { "id" });
      for (List<Object> parameters : allParameters) {
        log.trace("parameters = {}", parameters);
        for (int i = 0; i < parameters.size(); i++) {
          ps.setObject(i + 1, parameters.get(i));
        }
        ps.addBatch();
      }
      ps.executeBatch();

      Long[] keys = new Long[allParameters.size()];
      ResultSet ks = ps.getGeneratedKeys();
      int i = 0;
      while (ks.next()) {
        keys[i++] = ks.getLong(1);
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
      log.trace("sql = {}", sql);
      tickUpdatesIfTracking();
      ps = connection.prepareStatement(sql);
      for (List<Object> parameters : allParameters) {
        log.trace("parameters = {}", parameters);
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
        log.error("Error occurred closing {}", e, o);
      }
    }
  }

  private static void tickQueriesIfTracking() {
    if (trackStats) {
      queries.incrementAndGet();
    }
  }

  private static void tickUpdatesIfTracking() {
    if (trackStats) {
      updates.incrementAndGet();
    }
  }

}
