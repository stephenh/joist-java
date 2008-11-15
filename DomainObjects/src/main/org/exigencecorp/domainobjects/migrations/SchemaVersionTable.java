package org.exigencecorp.domainobjects.migrations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.exigencecorp.jdbc.Jdbc;

/** A simple wrapper around the <code>schema_version</code> table for the {@link Migrater} class. */
public class SchemaVersionTable {

    private DataSource dataSource;

    public SchemaVersionTable(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean tryToLock() {
        // Assume locked if no table exists yet
        boolean notAround = Jdbc.queryForInt(this.dataSource, "select count(*) from information_schema.tables where table_name = 'schema_version'") == 0;
        if (notAround) {
            return true;
        }
        return Jdbc.executeUpdate(this.dataSource, "UPDATE \"schema_version\" SET \"update_lock\" = 1 WHERE \"update_lock\" = 0") == 1;
    }

    public void unlock() {
        boolean around = Jdbc.queryForInt(this.dataSource, "select count(*) from information_schema.tables where table_name = 'schema_version'") == 1;
        if (around) {
            Jdbc.executeUpdate(this.dataSource, "UPDATE \"schema_version\" SET \"update_lock\" = 0");
        }
    }

    public void vacuumIfAppropriate() {
        Jdbc.executeUpdate(this.dataSource, "vacuum analyze");
    }

    /** @param conn the auto-commit=false connection for the current update. */
    public int nextVersionNumber(Connection conn) throws SQLException {
        boolean notAround = Jdbc.queryForInt(this.dataSource, "select count(*) from information_schema.tables where table_name = 'schema_version'") == 0;
        if (notAround) {
            return 0;
        }
        Statement s = null;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            rs = s.executeQuery("SELECT \"version\" FROM \"schema_version\"");
            rs.next();
            return rs.getInt(1) + 1;
        } finally {
            Jdbc.closeSafely(rs, s);
        }
    }

    /** @param conn the auto-commit=false connection for the current update. */
    public void updateVersionNumber(Connection conn, int nextVersion) throws SQLException {
        Statement s = null;
        try {
            s = conn.createStatement();
            s.executeUpdate("UPDATE \"schema_version\" SET \"version\" = " + nextVersion);
        } finally {
            Jdbc.closeSafely(s);
        }
    }

}
