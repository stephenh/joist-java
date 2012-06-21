package joist.migrations;

import java.io.File;

import javax.sql.DataSource;

import joist.codegen.Config;
import joist.jdbc.Jdbc;
import joist.util.Execute;
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

  public void backup() {
    if (this.config.db.isMySQL()) {
      this.backupMySQL();
    } else {
      this.backupPg();
    }
  }

  private void dropAndCreateMySQL() {
    String databaseName = this.config.dbAppUserSettings.databaseName;
    String username = this.config.dbAppUserSettings.user;
    String password = this.config.dbAppUserSettings.password;
    String userhost = this.config.userhost; // defaults to %

    DataSource systemDs = this.config.dbSystemSettings.getDataSource();
    int i = Jdbc.queryForInt(systemDs, "select count(*) from information_schema.schemata where schema_name = '{}'", databaseName);
    if (i != 0) {
      log.info("Dropping {}", databaseName);
      Jdbc.update(systemDs, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(systemDs, "select count(*) from mysql.user where user = '{}' and host = '{}'", username, userhost);
    if (j != 0) {
      log.info("Dropping '{}'@'{}'", username, userhost);
      Jdbc.update(systemDs, "revoke all privileges, grant option from '{}'@'{}'", username, userhost);
      Jdbc.update(systemDs, "drop user '{}'@'{}'", username, userhost);
    }

    log.info("Creating {}", databaseName);
    Jdbc.update(systemDs, "create database {};", databaseName);

    log.info("Creating '{}'@'{}'", username, userhost);
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
        .toSystemOut()
        .systemExitIfFailed();
    }
  }

  private void backupMySQL() {
    log.info("Backing up to {}", this.config.databaseBackupPath);
    new Execute("mysqldump")
      .addEnvPaths()
      .arg("--user=" + this.config.dbSystemSettings.user)
      .arg("--host=" + this.config.dbSystemSettings.host)
      .arg("--password=" + this.config.dbSystemSettings.password)
      .arg(this.config.dbAppUserSettings.databaseName)
      .toFile(this.config.databaseBackupPath)
      .systemExitIfFailed();
  }

  private void backupPg() {
    log.info("Backing up to {}", this.config.databaseBackupPath);
    new Execute("pg_dump")
      .addEnvPaths()
      .arg("--ignore-version")
      .arg("--host")
      .arg(this.config.dbSystemSettings.host)
      .arg("-U")
      .arg(this.config.dbSystemSettings.user)
      .env("PGPASSWORD", this.config.dbSystemSettings.password)
      // TODO eventually have an option to use binary
      .arg("--format=t")
      .arg("--no-owner")
      .arg("--no-privileges")
      .arg("--file=" + this.config.databaseBackupPath)
      .arg(this.config.dbAppUserSettings.databaseName)
      .toSystemOut()
      .systemExitIfFailed();
  }

  private void dropAndCreatePg() {
    String databaseName = this.config.dbAppUserSettings.databaseName;
    String username = this.config.dbAppUserSettings.user;
    String password = this.config.dbAppUserSettings.password;

    DataSource systemDs = this.config.dbSystemSettings.getDataSource();
    int i = Jdbc.queryForInt(systemDs, "select count(*) from pg_catalog.pg_database where datname = '{}'", databaseName);
    if (i != 0) {
      log.info("Dropping {}", databaseName);
      Jdbc.update(systemDs, "drop database {};", databaseName);
    }

    int j = Jdbc.queryForInt(systemDs, "select count(*) from pg_catalog.pg_user where usename = '{}'", username);
    if (j != 0) {
      log.info("Dropping {}", username);
      Jdbc.update(systemDs, "drop user {};", username);
    }

    log.info("Creating {}", databaseName);
    Jdbc.update(systemDs, "create database {} template template0;", databaseName);

    log.info("Creating {}", username);
    Jdbc.update(systemDs, "create user {} password '{}';", username, password);

    if (new File(this.config.databaseBackupPath).exists()) {
      log.info("Restoring {}", this.config.databaseBackupPath);
      new Execute("pg_restore")
        .addEnvPaths()
        .arg("--host")
        .arg(this.config.dbSystemSettings.host)
        .arg("-U")
        .arg(this.config.dbSystemSettings.user)
        .env("PGPASSWORD", this.config.dbSystemSettings.password)
        .arg("--dbname=" + databaseName)
        // TODO Support an option for binary, and do --schema-only/--data-only
        .arg("--format=t")
        .arg("--disable-triggers")
        .arg(this.config.databaseBackupPath)
        .toSystemOut()
        .systemExitIfFailed();
    } else {
      // the backup will contain plpgsql, so only issue this if it's a new database
      log.info("Creating plpgsql");
      DataSource appSaDs = this.config.dbAppSaSettings.getDataSource();
      Jdbc.update(appSaDs, "create language plpgsql;");
    }
  }

}
