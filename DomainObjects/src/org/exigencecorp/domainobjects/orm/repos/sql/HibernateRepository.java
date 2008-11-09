package org.exigencecorp.domainobjects.orm.repos.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.orm.repos.Repository;
import org.exigencecorp.jdbc.Jdbc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cache.NoCacheProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.ManagedSessionContext;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.transaction.JDBCTransactionFactory;

public class HibernateRepository implements Repository {

    private static SessionFactory sessionFactory;
    private Connection connection;
    private Session session;

    static {
        Configuration c = new Configuration();

        c.setProperty("hibernate.cache.use_second_level_cache", "false");
        c.setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());
        c.setProperty("hibernate.connection.autocommit", "false");
        c.setProperty("hibernate.current_session_context_class", ManagedSessionContext.class.getName());
        c.setProperty("hibernate.dialect", PostgreSQLDialect.class.getName());
        c.setProperty("hibernate.transaction.factory_class", JDBCTransactionFactory.class.getName());

        c.addInputStream(HibernateRepository.class.getResourceAsStream("/org/exigencecorp/domainobjects/orm/repos/sql/Parent.hbm.xml"));

        HibernateRepository.sessionFactory = c.buildSessionFactory();
    }

    public <T extends DomainObject> T load(Class<T> type, Integer id) {
        return (T) this.session.load(type, id);
    }

    public <T extends DomainObject> void delete(T instance) {
        this.session.delete(instance);
        this.session.flush();
    }

    public <T extends DomainObject> void store(Set<T> instances) {
        for (T instance : instances) {
            this.session.saveOrUpdate(instance);
        }
        this.session.flush();
    }

    public void open() {
        try {
            this.connection = JdbcRepository.THIS_IS_DUMB.getConnection();
            this.connection.setAutoCommit(false);
            this.session = HibernateRepository.sessionFactory.openSession(this.connection);
            Jdbc.executeUpdate(this.connection, "SET CONSTRAINTS ALL DEFERRED;");
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public void close() {
        try {
            this.connection.close();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public void rollback() {
        try {
            this.connection.rollback();
        } catch (SQLException se) {
            throw new RuntimeException(se);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Session getSession() {
        return this.session;
    }

}
