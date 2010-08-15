package joist.perf.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.pools.Pgc3p0Factory;

import org.hibernate.HibernateException;
import org.hibernate.connection.ConnectionProvider;

import com.mchange.v2.c3p0.DataSources;

public class MyConnectionProvider implements ConnectionProvider {

  private final DataSource ds = new Pgc3p0Factory(ConnectionSettings.forApp(Db.PG, "features")).create();

  public void configure(Properties props) throws HibernateException {
  }

  public Connection getConnection() throws SQLException {
    return this.ds.getConnection();
  }

  public void closeConnection(Connection conn) throws SQLException {
    conn.close();
  }

  public void close() {
    try {
      DataSources.destroy(this.ds);
    } catch (SQLException se) {
      throw new HibernateException(se);
    }
  }

  public boolean supportsAggressiveRelease() {
    return false;
  }
}
