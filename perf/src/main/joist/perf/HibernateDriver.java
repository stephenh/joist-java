package joist.perf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import joist.domain.util.AbstractPgWithc3p0DataSourceFactory;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cache.NoCacheProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.transaction.JDBCTransactionFactory;

import com.mchange.v2.c3p0.DataSources;
import com.sun.japex.TestCase;

import features.domain.Parent;

public class HibernateDriver extends com.sun.japex.JapexDriverBase {

    private SessionFactory sessionFactory;

    @Override
    public void initializeDriver() {

        Configuration c = new Configuration();
        c.setProperty("hibernate.cache.use_second_level_cache", "false");
        c.setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());
        c.setProperty("hibernate.connection.autocommit", "false");
        c.setProperty("hibernate.connection.provider_class", ConnectionProvider.class.getName());
        c.setProperty("hibernate.dialect", PostgreSQLDialect.class.getName());
        c.setProperty("hibernate.transaction.factory_class", JDBCTransactionFactory.class.getName());
        // c.setProperty("hibernate.current_session_context_class", ManagedSessionContext.class.getName());

        c.addResource("joist/perf/Parent.hbm.xml");

        this.sessionFactory = c.buildSessionFactory();
    }

    public void run(TestCase testCase) {
        int number = new Integer(testCase.getParam("number"));
        boolean commitOnEach = testCase.getBooleanParam("commitOnEach");
        boolean insert = testCase.getParam("type").equals("insert");
        Session s = null;
        Transaction t = null;
        if (!commitOnEach) {
            s = this.sessionFactory.openSession();
            t = s.beginTransaction();
        }

        for (int i = 0; i < number; i++) {
            if (commitOnEach) {
                s = this.sessionFactory.openSession();
                t = s.beginTransaction();
            }

            Parent p;
            if (insert) {
                p = new Parent(String.valueOf(i));
            } else {
                p = (Parent) s.load(Parent.class, new Integer(i + 1));
                p.setName(System.currentTimeMillis() + "");
            }
            s.save(p);

            if (commitOnEach) {
                t.commit();
                s.close();
            }
        }

        if (!commitOnEach) {
            t.commit();
            s.close();
        }
    }

    public static class ConnectionProvider implements org.hibernate.connection.ConnectionProvider {
        private final DataSource ds = new AbstractPgWithc3p0DataSourceFactory("features").create();

        public void configure(Properties props) throws HibernateException {
        }

        public Connection getConnection() throws SQLException {
            return this.ds.getConnection();
        }

        public void closeConnection(Connection conn) throws SQLException {
            conn.close();
        }

        public void close() {
            try {
                DataSources.destroy(this.ds);
            } catch (SQLException se) {
                throw new HibernateException(se);
            }
        }

        public boolean supportsAggressiveRelease() {
            return false;
        }
    }
}
