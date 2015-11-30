package joist.domain.util;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.util.pools.DataSourceFactory;
import joist.domain.util.pools.HikariDataSourceFactory;

public class ConnectionSettings {

  public Db db;
  public String schemaName;
  public String databaseName;
  public String user;
  public String password;
  public String host;
  public int maxPoolSize = 100;
  public int initialPoolSize = 1;
  public int timeoutInSeconds = 5;
  public DataSourceFactory factory = new HikariDataSourceFactory();
  private DataSource ds;

  public DataSource getDataSource() {
    if (this.ds == null) {
      this.ds = this.factory.create(this);
    }
    return this.ds;
  }

  public static ConnectionSettings forApp(Db db, String databaseName) {
    ConnectionSettings settings = new ConnectionSettings();
    settings.db = db;
    settings.host = System.getProperty("db.host", "localhost");
    settings.databaseName = System.getProperty("db.name", databaseName);
    settings.schemaName = db.isPg() ? "public" : settings.databaseName;
    settings.user = System.getProperty("db.username", databaseName + "_role");
    settings.password = System.getProperty("db.password", databaseName + "_role");
    return settings;
  }

  public static ConnectionSettings forAppSa(Db db, String databaseName) {
    ConnectionSettings settings = new ConnectionSettings();
    settings.db = db;
    settings.host = System.getProperty("db.host", "localhost");
    settings.databaseName = System.getProperty("db.name", databaseName);
    settings.schemaName = db.isPg() ? "public" : settings.databaseName;
    settings.user = System.getProperty("db.sa.username", db.isPg() ? "postgres" : "root");
    settings.password = System.getProperty("db.sa.password", ".");
    return settings;
  }

  public static ConnectionSettings forSystemSa(Db db, String databaseName) {
    ConnectionSettings settings = forAppSa(db, databaseName);
    settings.databaseName = db.isPg() ? "postgres" : "mysql";
    return settings;
  }

}
