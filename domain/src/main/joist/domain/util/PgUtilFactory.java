package joist.domain.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import joist.jdbc.JdbcConnectionPool;
import joist.registry.ResourceFactory;
import joist.util.Log;

import org.postgresql.ds.PGConnectionPoolDataSource;

public final class PgUtilFactory implements ResourceFactory<DataSource> {

    private final ConnectionSettings settings;
    private JdbcConnectionPool cp;

    public PgUtilFactory(ConnectionSettings settings) {
        this.settings = settings;
    }

    public DataSource create() {
        this.cp = JdbcConnectionPool.create(this.createPGConnectionPoolDataSource());
        this.cp.setMaxConnections(this.settings.maxPoolSize);
        return this.cp;
    }

    protected PGConnectionPoolDataSource createPGConnectionPoolDataSource() {
        PGConnectionPoolDataSource cpds = new PGConnectionPoolDataSource();
        cpds.setServerName(this.settings.host);
        cpds.setDatabaseName(this.settings.databaseName);
        cpds.setUser(this.settings.user);
        cpds.setPassword(this.settings.password);
        // use server-side PREPARE if over X rows--note, means planning only done once/statement
        cpds.setPrepareThreshold(5);
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
