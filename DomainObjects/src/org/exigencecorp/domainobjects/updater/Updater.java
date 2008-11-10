package org.exigencecorp.domainobjects.updater;

import java.sql.Connection;
import java.sql.SQLException;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Log;

public class Updater {

    private static ThreadLocal<Connection> current = new ThreadLocal<Connection>();
    private UpdaterConfig config;
    private SchemaVersionTable schemaInfoTable;
    private UpdateClasses updateClasses;

    public static Connection getConnection() {
        return Updater.current.get();
    }

    public Updater(UpdaterConfig config) {
        this.config = config;
        this.schemaInfoTable = new SchemaVersionTable(this.config.getDataSource());
        this.updateClasses = new UpdateClasses(this.config.getPackageNamesContainingUpdates());
    }

    public void performUpdates() {
        boolean locked = this.schemaInfoTable.tryToLock();
        if (!locked) {
            throw new RuntimeException("schema_info was already locked");
        }

        try {
            boolean tryNextUpdate = true;
            while (tryNextUpdate) {
                tryNextUpdate = this.performNextUpdateIfAvailable();
            }

            this.schemaInfoTable.vacuumIfAppropriate();
            // Flush the schema cache so PermissionFixer/FlushTestDatabase see newly-created tables
            // Schema.reload();
        } finally {
            this.schemaInfoTable.unlock();
        }
    }

    /** Starts a auto-commit=false connection/transaction for each update. */
    private boolean performNextUpdateIfAvailable() {
        Connection connection = null;
        try {
            connection = this.config.getDataSource().getConnection();
            connection.setAutoCommit(false);

            int nextVersion = this.schemaInfoTable.nextVersionNumber(connection);
            if (!this.updateClasses.hasUpdate(nextVersion)) {
                return false;
            }

            Updater.current.set(connection);

            if (this.config.getInitialConnectionSetupCommand() != null) {
                Jdbc.executeUpdate(connection, this.config.getInitialConnectionSetupCommand());
            }

            Update update = this.updateClasses.getUpdate(nextVersion);
            Log.info("Performing update " + update);
            update.apply();

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
