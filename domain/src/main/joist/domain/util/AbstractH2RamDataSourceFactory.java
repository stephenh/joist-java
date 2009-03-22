package joist.domain.util;

import java.sql.SQLException;

import javax.sql.DataSource;

import joist.registry.ResourceFactory;

import org.h2.jdbcx.JdbcConnectionPool;

public abstract class AbstractH2RamDataSourceFactory implements ResourceFactory<DataSource> {

    protected AbstractH2RamDataSourceFactory(String databaseName) {
    }

    public DataSource create() {
        return JdbcConnectionPool.create("jdbc:h2:mem:foo", "", "");
    }

    public void destroy(DataSource dataSource) {
        try {
            ((JdbcConnectionPool) dataSource).dispose();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
