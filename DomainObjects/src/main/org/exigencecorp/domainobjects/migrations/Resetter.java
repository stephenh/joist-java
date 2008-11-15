package org.exigencecorp.domainobjects.migrations;

import javax.sql.DataSource;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Log;

public class Resetter {

    private final DataSource systemDataSource;
    private final String databaseName;
    private final String username;
    private final String password;

    public Resetter(DataSource systemDataSource, String appDatabaseName, String appUsername, String appPassword) {
        this.systemDataSource = systemDataSource;
        this.databaseName = appDatabaseName;
        this.username = appUsername;
        this.password = appPassword;
    }

    public void reset() {
        int i = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_database where datname = '{}'", this.databaseName);
        if (i != 0) {
            Log.debug("Dropping {}", this.databaseName);
            Jdbc.executeUpdate(this.systemDataSource, "drop database {};", this.databaseName);
        }

        int j = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_user where usename = '{}'", this.username);
        if (j != 0) {
            Log.debug("Dropping {}", this.username);
            Jdbc.executeUpdate(this.systemDataSource, "drop user {};", this.username);
        }

        Log.debug("Creating {}", this.databaseName);
        Jdbc.executeUpdate(this.systemDataSource, "create database {};", this.databaseName);

        Log.debug("Creating {}", this.username);
        Jdbc.executeUpdate(this.systemDataSource, "create user {} password '{}';", this.username, this.password);
    }

}
