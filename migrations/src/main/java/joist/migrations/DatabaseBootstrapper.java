package joist.migrations;

import java.io.File;

import javax.sql.DataSource;

import joist.codegen.Config;
import joist.jdbc.Jdbc;
import joist.util.Execute;
import joist.util.Interpolate;
import joist.util.Write;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseBootstrapper {

  private static final Logger log = LoggerFactory.getLogger(DatabaseBootstrapper.class);
  private final Config config;

  public DatabaseBootstrapper(Config config) {
    this.config = config;
  }

  public void dropAndCreate() {
    String username = this.config.dbAppUserSettings.user;
    if ("root".equals(username) || "postgres".equals(username)) {
      String message = "You've set db.username={}, which is a system user, but during cycle Joist DROPs/CREATEs the application user."
        + " Please use an application-specific username.";
      throw new RuntimeException(Interpolate.string(message, username));
    }

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

    if ("localhost".equals(this.config.dbAppUserSettings.host)
      && "%".equals(userhost)
      && Jdbc.queryForInt(systemDs, "select count(*) from mysql.user WHERE user = '' and host = 'localhost'") > 0) {
      String message = "Found anonymous user ''@'localhost'."
        + " Due to MySQL's access control, the anonymous user will mask the application user {}@{}."
        + " You need to either set system property db.userhost to localhost or delete anonymous user";
      throw new RuntimeException(Interpolate.string(message, username, userhost));
    }

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

    String backupRestorationPath = this.getBackupRestorationPath();
    if (backupRestorationPath != null) {
      log.info("Restoring {}", backupRestorationPath);
      new Execute("mysql")
        .addEnvPaths()
        .arg("--user=" + this.config.dbSystemSettings.user)
        .arg("--host=" + this.config.dbSystemSettings.host)
        .arg("--password=" + this.config.dbSystemSettings.password)
        .arg(databaseName)
        .arg("--execute=source " + backupRestorationPath)
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

    String backupRestorationPath = this.getBackupRestorationPath();
    if (backupRestorationPath != null) {
      log.info("Restoring {}", backupRestorationPath);
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
        .arg(backupRestorationPath)
        .toSystemOut()
        .systemExitIfFailed();
    } else {
      // the backup will contain plpgsql, so only issue this if it's a new database
      DataSource appSaDs = this.config.dbAppSaSettings.getDataSource();
      if (Jdbc.queryForInt(appSaDs, "select count(*) FROM pg_language where lanname = 'plpgsql'") == 0) {
        log.info("Creating plpgsql");
        Jdbc.update(appSaDs, "create language plpgsql;");
      }
    }
  }

  private String getBackupRestorationPath() {
    if (this.config.databaseBackupPath != null && new File(this.config.databaseBackupPath).exists()) {
      return this.config.databaseBackupPath;
    } else if (this.config.databaseBackupResourceLocation != null) {
      return Write.classpathResourceToTempFile(this.config.databaseBackupResourceLocation).getAbsolutePath();
    } else {
      return null;
    }
  }

}
