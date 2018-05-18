package joist.domain.util.pools;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import joist.domain.util.ConnectionSettings;

/**
 * Creates connection pools baked by HikariCP.
 */
public class HikariDataSourceFactory implements DataSourceFactory {

  @Override
  public HikariDataSource create(ConnectionSettings settings) {
    HikariConfig config = new HikariConfig();
    this.addCommonOptions(settings, config);
    if (settings.db.isPg()) {
      this.addPostgresOptions(settings, config);
    } else if (settings.db.isMySQL()) {
      this.addMySQLOptions(settings, config);
    }
    this.addCustomOptions(settings, config);
    return new HikariDataSource(config);
  }

  protected void addCommonOptions(ConnectionSettings settings, HikariConfig config) {
    config.setUsername(settings.user);
    config.setPassword(settings.password);
    config.setConnectionTimeout((settings.timeoutInSeconds + 1) * 1000);
    config.setMinimumIdle(settings.initialPoolSize);
    config.setMaximumPoolSize(settings.maxPoolSize);
    config.addDataSourceProperty("databaseName", settings.databaseName);
    config.addDataSourceProperty("serverName", settings.host);
    config.setPoolName("joist-pool");
  }

  protected void addPostgresOptions(ConnectionSettings settings, HikariConfig config) {
    config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
    config.addDataSourceProperty("socketTimeout", settings.timeoutInSeconds);
  }

  protected void addMySQLOptions(ConnectionSettings settings, HikariConfig config) {
    config.setDataSourceClassName("com.mysql.cj.jdbc.MysqlDataSource");
    config.addDataSourceProperty("connectTimeout", (settings.timeoutInSeconds * 1000));
    config.addDataSourceProperty("socketTimeout", (settings.timeoutInSeconds * 1000));
    config.addDataSourceProperty("zeroDateTimeBehavior", "CONVERT_TO_NULL");
    // Default time zone values like CDT disappoint the MySQL jdbc driver, so opt-in to UTC
    config.addDataSourceProperty("serverTimezone", "UTC");
    // Silence SSL warning. This should probably be based on the host name? Not sure.
    config.addDataSourceProperty("useSSL", "false");
    // https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
    config.addDataSourceProperty("prepStmtCacheSize", 500);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    config.addDataSourceProperty("cachePrepStmts", true);
  }

  protected void addCustomOptions(ConnectionSettings settings, HikariConfig config) {
  }

  public void destroy(DataSource dataSource) {
    ((HikariDataSource) dataSource).close();
  }
}
