package joist.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.util.ConnectionSettings;
import joist.jdbc.Jdbc;
import joist.util.Log;

public class Migrater {

    private static ThreadLocal<Connection> current = new ThreadLocal<Connection>();
    private final MigraterConfig config;
    private final DataSource dataSource;
    private final SchemaVersionTable schemaInfoTable;
    private final MigrationLoader migrationClasses;

    public static Connection getConnection() {
        return Migrater.current.get();
    }

    public Migrater(Db db, ConnectionSettings dbAppSettings, DataSource saDataSource, MigraterConfig config) {
        this.config = config;
        this.dataSource = saDataSource;
        this.schemaInfoTable = new SchemaVersionTable(db, dbAppSettings, saDataSource);
        this.migrationClasses = new MigrationLoader(this.config.packageNamesContainingMigrations);
        MigrationKeywords.db = db;
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
            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);

            int nextVersion = this.schemaInfoTable.nextVersionNumber(connection);
            if (!this.migrationClasses.hasMigration(nextVersion)) {
                return false;
            }

            Migrater.current.set(connection);

            if (this.config.getInitialConnectionSetupCommand() != null) {
                Jdbc.update(connection, this.config.getInitialConnectionSetupCommand());
            }

            Migration migration = this.migrationClasses.get(nextVersion);
            Log.info("Applying {}: {}", migration.getClass().getSimpleName(), migration.toString());
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
            Jdbc.closeSafely(connection);
        }
    }

}
