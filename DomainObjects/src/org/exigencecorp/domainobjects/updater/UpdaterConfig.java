package org.exigencecorp.domainobjects.updater;

import javax.sql.DataSource;

public abstract class UpdaterConfig {

    public abstract DataSource getDataSource();

    public abstract String[] getPackageNamesContainingUpdates();

    public String getInitialConnectionSetupCommand() {
        return "SET CONSTRAINTS ALL DEFERRED; SET CLIENT_ENCODING TO 'UNICODE';";
    }

}
