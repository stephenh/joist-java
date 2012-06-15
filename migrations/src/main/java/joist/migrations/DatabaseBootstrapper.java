package joist.migrations;

import java.io.File;

import javax.sql.DataSource;

import joist.codegen.Config;
import joist.jdbc.Jdbc;
import joist.util.Execute;
import joist.util.Execute.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseBootstrapper {

  private final Config config;

  public DatabaseBootstrapper(Config config) {
    this.config = config;
  }

  public void dropAndCreate() {
    if (this.config.db.isPg()) {
      this.dropAndCreatePg();
    } else if (this.config.db.isMySQL()) {
      this.dropAndCreateMySQL();
    } else {
      throw new IllegalStateException("Unhandled db " + this.config.db);
    }
  }

  public void restore(String pgBinPath) {
    log.debug("Schema only");
    Result result = this.restore(pgBinPath, "--schema-only");

    log.debug("Data only");
    result = this.restore(pgBinPath, "--data-only");
    if (!result.success) {
      log.error("Failed data load");
      log.error(result.out);
      log.error(result.err);
    }
  }

  private Result restore(String pgBinPath, String finalArgument) {
    return new Execute("pg_restore")//
      .path(pgBinPath)
      .env("PGPASSWORD", "")
      .arg("--dbname=" + this.config.dbAppUserSettings.databaseName)
      .arg("--username=sa")
      .arg("--host=localhost")
      .arg("--format=c")
      .arg("--disable-triggers")
      .arg(finalArgument)
      .toBuffer();
  }

  private void dropAndCreateMySQL() {
    String databaseName = this.config.dbAppUserSettings.databaseName;
    String username = this.config.dbAppUserSettings.user;
    String password = this.config.dbAppUserSettings.password;
    String userhost = this.config.userhost; // defaults to %

    DataSource systemDs = this.config.dbSystemSettings.getDataSource();
    int i = Jdbc.queryForInt(systemDs, "select count(*) from information_schema.schemata where schema_name = '{}'", databaseName);
    if (i != 0) {
      log.debug("Dropping {}", databaseName);
      Jdbc.update(systemDs, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(systemDs, "select count(*) from mysql.user where user = '{}' and host = '{}'", username, userhost);
    if (j != 0) {
      log.debug("Dropping '{}'@'{}'", username, userhost);
      Jdbc.update(systemDs, "revoke all privileges, grant option from '{}'@'{}'", username, userhost);
      Jdbc.update(systemDs, "drop user '{}'@'{}'", username, userhost);
    }

    log.debug("Creating {}", databaseName);
    Jdbc.update(systemDs, "create database {};", databaseName);

    log.debug("Creating '{}'@'{}'", username, userhost);
    Jdbc.update(systemDs, "create user '{}'@'{}' identified by '{}';", username, userhost, password);

    Jdbc.update(systemDs, "set global sql_mode = 'ANSI';", username, password);

    if (new File(this.config.databaseBackupPath).exists()) {
      log.info("Restoring {}", this.config.databaseBackupPath);
      new Execute("mysql")
        .addEnvPaths()
        .arg("--user=" + this.config.dbSystemSettings.user)
        .arg("--host=" + this.config.dbSystemSettings.host)
        .arg("--password=" + this.config.dbSystemSettings.password)
        .arg(databaseName)
        .arg("--execute=source " + this.config.databaseBackupPath)
        .toSystemOut();
    }
  }

  private void dropAndCreatePg() {
    String databaseName = this.config.dbAppUserSettings.databaseName;
    String username = this.config.dbAppUserSettings.user;
    String password = this.config.dbAppUserSettings.password;

    DataSource systemDs = this.config.dbSystemSettings.getDataSource();
    int i = Jdbc.queryForInt(systemDs, "select count(*) from pg_catalog.pg_database where datname = '{}'", databaseName);
    if (i != 0) {
      log.debug("Dropping {}", databaseName);
      Jdbc.update(systemDs, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(systemDs, "select count(*) from pg_catalog.pg_user where usename = '{}'", username);
    if (j != 0) {
      log.debug("Dropping {}", username);
      Jdbc.update(systemDs, "drop user {};", username);
    }

    log.debug("Creating {}", databaseName);
    Jdbc.update(systemDs, "create database {} template template0;", databaseName);

    log.debug("Creating {}", username);
    Jdbc.update(systemDs, "create user {} password '{}';", username, password);

    log.debug("Creating plpgsql");
    Jdbc.update(systemDs, "create language plpgsql;");
  }
}
