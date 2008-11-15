package org.exigencecorp.domainobjects.migrations;

import javax.sql.DataSource;

public abstract class MigraterConfig {

    public abstract DataSource getDataSource();

    public abstract String[] getPackageNamesContainingMigrations();

    public String getInitialConnectionSetupCommand() {
        return "SET CONSTRAINTS ALL DEFERRED; SET CLIENT_ENCODING TO 'UNICODE';";
    }

}
