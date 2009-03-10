package joist.domain.migrations;

import javax.sql.DataSource;

import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.Execute;
import org.exigencecorp.util.Log;
import org.exigencecorp.util.Execute.Result;

public class DatabaseBootstrapper {

    private final DataSource systemDataSource;
    private final String databaseName;
    private final String username;
    private final String password;

    public DatabaseBootstrapper(DataSource systemDataSource, String appDatabaseName, String appUsername, String appPassword) {
        this.systemDataSource = systemDataSource;
        this.databaseName = appDatabaseName;
        this.username = appUsername;
        this.password = appPassword;
    }

    public void dropAndCreate() {
        int i = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_database where datname = '{}'", this.databaseName);
        if (i != 0) {
            Log.debug("Dropping {}", this.databaseName);
            Jdbc.update(this.systemDataSource, "drop database {};", this.databaseName);
        }

        int j = Jdbc.queryForInt(this.systemDataSource, "select count(*) from pg_catalog.pg_user where usename = '{}'", this.username);
        if (j != 0) {
            Log.debug("Dropping {}", this.username);
            Jdbc.update(this.systemDataSource, "drop user {};", this.username);
        }

        Log.debug("Creating {}", this.databaseName);
        Jdbc.update(this.systemDataSource, "create database {};", this.databaseName);

        Log.debug("Creating {}", this.username);
        Jdbc.update(this.systemDataSource, "create user {} password '{}';", this.username, this.password);
    }

    public void restore(String pgBinPath) {
        Log.debug("Schema only");
        Result result = this.restore(pgBinPath, "--schema-only");

        Log.debug("Data only");
        result = this.restore(pgBinPath, "--data-only");
        if (!result.success) {
            Log.error("Failed data load");
            Log.error(result.out);
            Log.error(result.err);
        }
    }

    private Result restore(String pgBinPath, String finalArgument) {
        return new Execute("pg_restore")//
            .path(pgBinPath)
            .env("PGPASSWORD", "")
            .arg("--dbname=" + this.databaseName)
            .arg("--username=sa")
            .arg("--host=localhost")
            .arg("--format=c")
            .arg("--disable-triggers")
            .arg(finalArgument)
            .toBuffer();
    }
}
