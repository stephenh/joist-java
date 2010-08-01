package joist.migrations;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import joist.domain.util.ConnectionSettings;
import joist.jdbc.Jdbc;

/** A simple wrapper around the <code>schema_version</code> table for the {@link Migrater} class. */
public class SchemaVersionTable {

    private final DataSource dataSource;
    private final String schemaName;

    public SchemaVersionTable(ConnectionSettings dbAppSettings, DataSource dataSource) {
        this.dataSource = dataSource;
        this.schemaName = dbAppSettings.schemaName;
    }

    public boolean tryToLock() {
        // Assume locked if no table exists yet
        if (!this.isAround()) {
            return true;
        }
        return Jdbc.update(this.dataSource, "UPDATE schema_version SET update_lock = 1 WHERE update_lock = 0") == 1;
    }

    public void unlock() {
        if (this.isAround()) {
            Jdbc.update(this.dataSource, "UPDATE schema_version SET update_lock = 0");
        }
    }

    public void vacuumIfAppropriate() {
        // Jdbc.update(this.dataSource, "vacuum analyze");
    }

    /** @param conn the auto-commit=false connection for the current update. */
    public int nextVersionNumber(Connection conn) throws SQLException {
        if (!this.isAround()) {
            return 0;
        }
        return Jdbc.queryForInt(conn, "SELECT version FROM schema_version") + 1;
    }

    /** @param conn the auto-commit=false connection for the current update. */
    public void updateVersionNumber(Connection conn, int nextVersion) throws SQLException {
        Jdbc.update(conn, "UPDATE schema_version SET version = {}", nextVersion);
    }

    private boolean isAround() {
        return Jdbc.queryForInt(
            this.dataSource,
            "select count(*) from information_schema.tables where table_name = 'schema_version' and table_schema = '{}'",
            this.schemaName) > 0;
    }
}
