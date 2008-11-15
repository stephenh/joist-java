package org.exigencecorp.domainobjects.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Log;

public class Migrater {

    private static ThreadLocal<Connection> current = new ThreadLocal<Connection>();
    private MigraterConfig config;
    private SchemaVersionTable schemaInfoTable;
    private MigrationClasses migrationClasses;

    public static Connection getConnection() {
        return Migrater.current.get();
    }

    public Migrater(MigraterConfig config) {
        this.config = config;
        this.schemaInfoTable = new SchemaVersionTable(this.config.getDataSource());
        this.migrationClasses = new MigrationClasses(this.config.getPackageNamesContainingMigrations());
    }

    public void performMigrations() {
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
            // Flush the schema cache so PermissionFixer/FlushTestDatabase see newly-created tables
            // Schema.reload();
        } finally {
            this.schemaInfoTable.unlock();
        }
    }

    /** Starts a auto-commit=false connection/transaction for each migration. */
    private boolean performNextMigrationIfAvailable() {
        Connection connection = null;
        try {
            connection = this.config.getDataSource().getConnection();
            connection.setAutoCommit(false);

            int nextVersion = this.schemaInfoTable.nextVersionNumber(connection);
            if (!this.migrationClasses.hasMigration(nextVersion)) {
                return false;
            }

            Migrater.current.set(connection);

            if (this.config.getInitialConnectionSetupCommand() != null) {
                Jdbc.executeUpdate(connection, this.config.getInitialConnectionSetupCommand());
            }

            AbstractMigration migration = this.migrationClasses.get(nextVersion);
            Log.info("Performing migration " + migration);
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
