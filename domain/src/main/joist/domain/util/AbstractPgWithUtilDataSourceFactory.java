package joist.domain.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import joist.jdbc.JdbcConnectionPool;
import joist.registry.ResourceFactory;
import joist.util.Log;

import org.postgresql.ds.PGConnectionPoolDataSource;

public class AbstractPgWithUtilDataSourceFactory implements ResourceFactory<DataSource> {

    protected String databaseName;
    protected String user;
    protected String password;
    protected String host = "localhost";
    protected int maxPoolSize = 3;
    protected JdbcConnectionPool cp;

    protected AbstractPgWithUtilDataSourceFactory(String databaseName) {
        this.databaseName = System.getProperty("db.name", databaseName);
        this.user = System.getProperty("db.username", databaseName + "_role");
        this.password = System.getProperty("db.password", databaseName + "_role");
    }

    public DataSource create() {
        this.cp = JdbcConnectionPool.create(this.createPGConnectionPoolDataSource());
        this.cp.setMaxConnections(this.maxPoolSize);
        return this.cp;
    }

    protected PGConnectionPoolDataSource createPGConnectionPoolDataSource() {
        PGConnectionPoolDataSource cpds = new PGConnectionPoolDataSource();
        cpds.setServerName(this.host);
        cpds.setDatabaseName(this.databaseName);
        cpds.setUser(this.user);
        cpds.setPassword(this.password);
        return cpds;
    }

    public void destroy(DataSource dataSource) {
        try {
            this.cp.dispose();
        } catch (SQLException se) {
            Log.error("Error destroying dataSource {}", se, dataSource);
        }
    }

}
