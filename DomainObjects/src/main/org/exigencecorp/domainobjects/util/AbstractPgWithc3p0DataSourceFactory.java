package org.exigencecorp.domainobjects.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.exigencecorp.registry.ResourceFactory;
import org.exigencecorp.util.Log;
import org.exigencecorp.util.Reflection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public abstract class AbstractPgWithc3p0DataSourceFactory implements ResourceFactory<DataSource> {

    static {
        // Save 50-100ms because we don't care about JMX/whatever
        System.setProperty("com.mchange.v2.c3p0.management.ManagementCoordinator", "com.mchange.v2.c3p0.management.NullManagementCoordinator");
        // Save 50ms because we don't care about JNDI
        System.setProperty("com.mchange.v2.c3p0.VMID", "NONE");
    }

    private final String databaseName;

    protected AbstractPgWithc3p0DataSourceFactory(String databaseName) {
        this.databaseName = databaseName;
    }

    public ComboPooledDataSource create() {
        Reflection.forName("org.postgresql.Driver");

        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost/" + this.databaseName);
        ds.setUser(this.databaseName + "_role");
        ds.setPassword(this.databaseName + "_role");
        ds.setMaxPoolSize(3);
        ds.setInitialPoolSize(1);
        ds.setPreferredTestQuery("select 1");
        ds.setTestConnectionOnCheckout(true);
        return ds;
    }

    public void destroy(DataSource dataSource) {
        try {
            DataSources.destroy(dataSource);
        } catch (SQLException se) {
            Log.error("Error destroying dataSource {}", se, dataSource);
        }
    }

}
