package joist.domain.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import joist.registry.ResourceFactory;
import joist.util.Log;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

public class AbstractPgWithc3p0DataSourceFactory implements ResourceFactory<DataSource> {

    static {
        AbstractPgWithc3p0DataSourceFactory.setDefaultc3p0Flags();
    }

    public static void setDefaultc3p0Flags() {
        // Save 50-100ms because we don't care about JMX/whatever
        System.setProperty("com.mchange.v2.c3p0.management.ManagementCoordinator", "com.mchange.v2.c3p0.management.NullManagementCoordinator");
        // Save 50ms because we don't care about JNDI
        System.setProperty("com.mchange.v2.c3p0.VMID", "NONE");
    }

    protected String databaseName;
    protected String user;
    protected String password;
    protected String host = "localhost";
    protected int maxPoolSize = 3;
    protected int initialPoolSize = 1;

    protected AbstractPgWithc3p0DataSourceFactory(String databaseName) {
        this.databaseName = System.getProperty("db.name", databaseName);
        this.user = System.getProperty("db.username", databaseName + "_role");
        this.password = System.getProperty("db.password", databaseName + "_role");
    }

    public ComboPooledDataSource create() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setJdbcUrl("jdbc:postgresql://" + this.host + "/" + this.databaseName);
        ds.setUser(this.user);
        ds.setPassword(this.password);
        ds.setMaxPoolSize(this.maxPoolSize);
        ds.setInitialPoolSize(this.initialPoolSize);
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
