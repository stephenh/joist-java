package joist.migrations;

import java.io.File;

import javax.sql.DataSource;

import joist.codegen.CodegenConfig;
import joist.domain.orm.Db;
import joist.domain.util.ConnectionSettings;
import joist.jdbc.Jdbc;
import joist.util.Execute;
import joist.util.Execute.Result;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseBootstrapper {

  private final CodegenConfig config;
  private final DataSource systemDataSource;
  private final DataSource appDataSource;
  private final ConnectionSettings saSettings;
  private final ConnectionSettings appSettings;

  public DatabaseBootstrapper(
    CodegenConfig config,
    DataSource systemDataSource,
    DataSource appDataSource,
    ConnectionSettings saSettings,
    ConnectionSettings appSettings) {
    this.config = config;
    this.systemDataSource = systemDataSource;
    this.appDataSource = appDataSource;
    this.saSettings = saSettings;
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
      log.debug("Dropping {}", databaseName);
      Jdbc.update(this.systemDataSource, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(this.systemDataSource, "select count(*) from mysql.user where user = '{}'", username);
    if (j != 0) {
      log.debug("Dropping {}", username);
      Jdbc.update(this.systemDataSource, "revoke all privileges, grant option from {}", username);
      Jdbc.update(this.systemDataSource, "drop user {}", username);
    }

    log.debug("Creating {}", databaseName);
    Jdbc.update(this.systemDataSource, "create database {};", databaseName);

    log.debug("Creating {}", username);
    Jdbc.update(this.systemDataSource, "create user {} identified by '{}';", username, password);

    Jdbc.update(this.systemDataSource, "set global sql_mode = 'ANSI';", username, password);

    String backupPath = this.config.databaseBackupPath + File.separator + databaseName + ".sql";
    if (new File(backupPath).exists()) {
      log.info("Restoring {}", backupPath);
      new Execute("mysql")
        .addEnvPaths()
        .arg("--user=" + this.saSettings.user)
        .arg("--host=" + this.saSettings.host)
        .arg("--password=" + this.saSettings.password)
        .arg(databaseName)
        .arg("--execute=source " + backupPath)
        .toSystemOut();
    }
  }

  private void dropAndCreatePg() {
    String databaseName = this.appSettings.databaseName;
    String username = this.appSettings.user;
    String password = this.appSettings.password;

    int i = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_database where datname = '{}'", databaseName);
    if (i != 0) {
      log.debug("Dropping {}", databaseName);
      Jdbc.update(this.systemDataSource, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_user where usename = '{}'", username);
    if (j != 0) {
      log.debug("Dropping {}", username);
      Jdbc.update(this.systemDataSource, "drop user {};", username);
    }

    log.debug("Creating {}", databaseName);
    Jdbc.update(this.systemDataSource, "create database {} template template0;", databaseName);

    log.debug("Creating {}", username);
    Jdbc.update(this.systemDataSource, "create user {} password '{}';", username, password);

    log.debug("Creating plpgsql");
    Jdbc.update(this.appDataSource, "create language plpgsql;");
  }
}
