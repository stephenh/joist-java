package org.exigencecorp.updater;

import javax.sql.DataSource;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Log;

public class Resetter {

    private ResetterConfig config;

    public Resetter(ResetterConfig config) {
        this.config = config;
    }

    public void reset() {
        DataSource ds = this.config.getDataSource();
        String databaseName = this.config.getDatabaseName();
        String username = this.config.getUsername();
        String password = this.config.getPassword();

        int i = Jdbc.queryForInt(ds, "select count(*) from pg_catalog.pg_database where datname = '{}'", databaseName);
        if (i != 0) {
            Log.debug("Dropping {}", databaseName);
            Jdbc.executeUpdate(ds, "drop database {};", databaseName);
        }

        int j = Jdbc.queryForInt(ds, "select count(*) from pg_catalog.pg_user where usename = '{}'", username);
        if (j != 0) {
            Log.debug("Dropping {}", username);
            Jdbc.executeUpdate(ds, "drop user {};", username);
        }

        Log.debug("Creating {}", databaseName);
        Jdbc.executeUpdate(ds, "create database {};", databaseName);

        Log.debug("Creating {}", username);
        Jdbc.executeUpdate(ds, "create user {} password '{}';", username, password);
    }

}
