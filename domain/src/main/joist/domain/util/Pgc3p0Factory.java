package joist.domain.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import joist.registry.ResourceFactory;
import joist.util.Log;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.mchange.v2.c3p0.management.ManagementCoordinator;
import com.mchange.v2.c3p0.management.NullManagementCoordinator;

public final class Pgc3p0Factory implements ResourceFactory<DataSource> {

    static {
        Pgc3p0Factory.setDefaultc3p0Flags();
    }

    public static void setDefaultc3p0Flags() {
        // Save 50-100ms because we don't care about JMX/whatever
        System.setProperty(ManagementCoordinator.class.getName(), NullManagementCoordinator.class.getName());
        // Save 50ms because we don't care about JNDI
        System.setProperty("com.mchange.v2.c3p0.VMID", "NONE");
    }

    private final ConnectionSettings settings;

    protected Pgc3p0Factory(ConnectionSettings settings) {
        this.settings = settings;
    }

    public ComboPooledDataSource create() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setJdbcUrl("jdbc:postgresql://" + this.settings.host + "/" + this.settings.databaseName);
        ds.setUser(this.settings.user);
        ds.setPassword(this.settings.password);
        ds.setMaxPoolSize(this.settings.maxPoolSize);
        ds.setInitialPoolSize(this.settings.initialPoolSize);
        // ds.setPreferredTestQuery("select 1");
        ds.setTestConnectionOnCheckout(false); // true);
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
