package joist.domain.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import joist.jdbc.JdbcConnectionPool;
import joist.registry.ResourceFactory;
import joist.util.Log;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public final class MySqlUtilFactory implements ResourceFactory<DataSource> {

    private final ConnectionSettings settings;
    private JdbcConnectionPool cp;

    public MySqlUtilFactory(ConnectionSettings settings) {
        this.settings = settings;
    }

    public DataSource create() {
        this.cp = JdbcConnectionPool.create(this.createPGConnectionPoolDataSource());
        this.cp.setMaxConnections(this.settings.maxPoolSize);
        return this.cp;
    }

    protected MysqlConnectionPoolDataSource createPGConnectionPoolDataSource() {
        MysqlConnectionPoolDataSource cpds = new MysqlConnectionPoolDataSource();
        cpds.setServerName(this.settings.host);
        cpds.setDatabaseName(this.settings.databaseName);
        cpds.setUser(this.settings.user);
        cpds.setPassword(this.settings.password);
        // cpds.setRewriteBatchedStatements(true);
        cpds.setUseServerPrepStmts(true);
        cpds.setEmulateUnsupportedPstmts(false);
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
