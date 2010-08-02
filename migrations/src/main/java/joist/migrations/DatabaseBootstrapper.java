package joist.migrations;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.util.ConnectionSettings;
import joist.jdbc.Jdbc;
import joist.util.Execute;
import joist.util.Execute.Result;
import joist.util.Log;

public class DatabaseBootstrapper {

  private final DataSource systemDataSource;
  private final DataSource appDataSource;
  private final ConnectionSettings appSettings;

  public DatabaseBootstrapper(DataSource systemDataSource, DataSource appDataSource, ConnectionSettings appSettings) {
    this.systemDataSource = systemDataSource;
    this.appDataSource = appDataSource;
    this.appSettings = appSettings;
  }

  public void dropAndCreate() {
    Db db = this.appSettings.db;
    if (db.isPg()) {
      this.dropAndCreatePg();
    } else if (db.isMySQL()) {
      this.dropAndCreateMySQL();
    } else {
      throw new IllegalStateException("Unhandled db " + db);
    }
  }

  public void restore(String pgBinPath) {
    Log.debug("Schema only");
    Result result = this.restore(pgBinPath, "--schema-only");

    Log.debug("Data only");
    result = this.restore(pgBinPath, "--data-only");
    if (!result.success) {
      Log.error("Failed data load");
      Log.error(result.out);
      Log.error(result.err);
    }
  }

  private Result restore(String pgBinPath, String finalArgument) {
    return new Execute("pg_restore")//
      .path(pgBinPath)
      .env("PGPASSWORD", "")
      .arg("--dbname=" + this.appSettings.databaseName)
      .arg("--username=sa")
      .arg("--host=localhost")
      .arg("--format=c")
      .arg("--disable-triggers")
      .arg(finalArgument)
      .toBuffer();
  }

  private void dropAndCreateMySQL() {
    String databaseName = this.appSettings.databaseName;
    String username = this.appSettings.user;
    String password = this.appSettings.password;

    int i = Jdbc.queryForInt(this.systemDataSource, "select count(*) from information_schema.schemata where schema_name = '{}'", databaseName);
    if (i != 0) {
      Log.debug("Dropping {}", databaseName);
      Jdbc.update(this.systemDataSource, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(this.systemDataSource, "select count(*) from mysql.user where user = '{}'", username);
    if (j != 0) {
      Log.debug("Dropping {}", username);
      Jdbc.update(this.systemDataSource, "revoke all privileges, grant option from {}", username);
      Jdbc.update(this.systemDataSource, "drop user {}", username);
    }

    Log.debug("Creating {}", databaseName);
    Jdbc.update(this.systemDataSource, "create database {};", databaseName);

    Log.debug("Creating {}", username);
    Jdbc.update(this.systemDataSource, "create user {} identified by '{}';", username, password);
  }

  private void dropAndCreatePg() {
    String databaseName = this.appSettings.databaseName;
    String username = this.appSettings.user;
    String password = this.appSettings.password;

    int i = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_database where datname = '{}'", databaseName);
    if (i != 0) {
      Log.debug("Dropping {}", databaseName);
      Jdbc.update(this.systemDataSource, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_user where usename = '{}'", username);
    if (j != 0) {
      Log.debug("Dropping {}", username);
      Jdbc.update(this.systemDataSource, "drop user {};", username);
    }

    Log.debug("Creating {}", databaseName);
    Jdbc.update(this.systemDataSource, "create database {} template template0;", databaseName);

    Log.debug("Creating {}", username);
    Jdbc.update(this.systemDataSource, "create user {} password '{}';", username, password);

    Log.debug("Creating plpgsql");
    Jdbc.update(this.appDataSource, "create language plpgsql;");
  }
}