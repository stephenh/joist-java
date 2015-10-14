package joist.migrations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joist.codegen.Config;
import joist.jdbc.Jdbc;
import joist.jdbc.JdbcException;

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
      DataSource ds = this.config.dbAppSaSettings.getDataSource();
      this.applyNormalMigrations(ds);
      this.applyBranchMigrationsIfAllowed(ds);
      this.schemaInfoTable.vacuumIfAppropriate();
    } finally {
      this.schemaInfoTable.unlock();
    }
  }

  private void applyNormalMigrations(DataSource ds) {
    int version = Jdbc.inTransaction(ds, c -> this.schemaInfoTable.nextVersionNumber(c));
    while (true) {
      Optional<Migration> migration = this.migrationClasses.get("m", version);
      if (migration.isPresent()) {
        this.apply(ds, migration.get(), Optional.of(version));
        version++;
      } else {
        break;
      }
    }
  }

  private void applyBranchMigrationsIfAllowed(DataSource ds) {
    if (this.config.allowBranchMigrations) {
      this.applyBranchMigrations(ds);
    } else {
      Optional<Migration> b0 = this.migrationClasses.get("b", 0);
      if (b0.isPresent()) {
        throw new IllegalStateException("Found branch migration " + b0.get().getClass() + " but allowBranchMigrations=false");
      }
    }
  }

  private void applyBranchMigrations(DataSource ds) {
    int version = 0; // branch migrations always start from zero
    while (true) {
      Optional<Migration> migration = this.migrationClasses.get("b", version);
      if (migration.isPresent()) {
        this.apply(ds, migration.get(), Optional.empty());
        version++;
      } else {
        break;
      }
    }
  }

  private void apply(DataSource ds, Migration migration, Optional<Integer> version) {
    Jdbc.inTransaction(ds, connection -> {
      Migrater.current.set(connection);
      log.info("Applying {}: {}", migration.getClass().getSimpleName(), migration.toString());
      // Set the updater in case any history triggers are listening
      Jdbc.update(connection, "set @updater=?", migration.getClass().getSimpleName());
      try {
        migration.apply();
      } catch (SQLException se) {
        throw new JdbcException(se);
      }
      // Tick to the current version number
      version.ifPresent(v -> this.schemaInfoTable.updateVersionNumber(connection, v));
      Jdbc.update(connection, "set @updater=null");
      return null;
    });
  }

}
