package joist.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import joist.codegen.Config;
import joist.jdbc.Jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Migrater {

  private static final Logger log = LoggerFactory.getLogger(Migrater.class);
  private static ThreadLocal<Connection> current = new ThreadLocal<Connection>();
  private final Config config;
  private final SchemaVersionTable schemaInfoTable;
  private final MigrationLoader migrationClasses;

  public static Connection getConnection() {
    return Migrater.current.get();
  }

  public Migrater(Config config) {
    this.config = config;
    this.schemaInfoTable = new SchemaVersionTable(config);
    this.migrationClasses = new MigrationLoader(this.config.packageNamesContainingMigrations);
    MigrationKeywords.config = config;
  }

  public void migrate() {
    boolean locked = this.schemaInfoTable.tryToLock();
    if (!locked) {
      throw new RuntimeException("schema_info was already locked");
    }
    try {
      boolean tryNextMigration = true;
      while (tryNextMigration) {
        tryNextMigration = this.performNextMigrationIfAvailable();
      }
      this.schemaInfoTable.vacuumIfAppropriate();
    } finally {
      this.schemaInfoTable.unlock();
    }
  }

  /** Starts a auto-commit=false connection/transaction for each migration. */
  private boolean performNextMigrationIfAvailable() {
    Connection connection = null;
    try {
      connection = this.config.dbAppSaSettings.getDataSource().getConnection();
      connection.setAutoCommit(false);

      int nextVersion = this.schemaInfoTable.nextVersionNumber(connection);
      if (!this.migrationClasses.hasMigration(nextVersion)) {
        return false;
      }

      Migrater.current.set(connection);

      // TODO I forget what this is for...
      // if (this.config.getInitialConnectionSetupCommand() != null) {
      //  Jdbc.update(connection, this.config.getInitialConnectionSetupCommand());
      // }

      Migration migration = this.migrationClasses.get(nextVersion);
      log.info("Applying {}: {}", migration.getClass().getSimpleName(), migration.toString());
      // Set the updater in case any history triggers are listening
      Jdbc.update(connection, "set @updater=?", migration.getClass().getSimpleName());
      migration.apply();

      // Tick to the current version number
      this.schemaInfoTable.updateVersionNumber(connection, nextVersion);

      connection.commit();
      return true;
    } catch (ClassNotFoundException cnfe) {
      throw new RuntimeException(cnfe);
    } catch (SQLException se) {
      throw new RuntimeException(se);
    } finally {
      try {
        Jdbc.update(connection, "set @updater=null");
      } catch (Exception e) {
        log.warn("Ignoring exception in finally", e);
      }
      Jdbc.closeSafely(connection);
    }
  }

}
