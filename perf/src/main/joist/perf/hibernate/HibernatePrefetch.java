package joist.perf.hibernate;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.pools.Pgc3p0Factory;
import joist.jdbc.Jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cache.NoCacheProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.transaction.JDBCTransactionFactory;

public class HibernatePrefetch {

    private static SessionFactory buildSessionFactory() {
        Configuration c = new Configuration();
        c.setProperty("hibernate.cache.use_second_level_cache", "false");
        c.setProperty("hibernate.cache.provider_class", NoCacheProvider.class.getName());
        c.setProperty("hibernate.connection.autocommit", "false");
        c.setProperty("hibernate.connection.provider_class", MyConnectionProvider.class.getName());
        c.setProperty("hibernate.dialect", PostgreSQLDialect.class.getName());
        c.setProperty("hibernate.transaction.factory_class", JDBCTransactionFactory.class.getName());
        c.setProperty("hibernate.show_sql", "true");
        c.addResource("joist/perf/hibernate/objects.hbm.xml");
        return c.buildSessionFactory();
    }

    public static void main(String[] args) {
        final DataSource ds = new Pgc3p0Factory(ConnectionSettings.forApp(Db.PG, "features")).create();
        Jdbc.queryForInt(ds, "select * from flush_test_database()");
        System.out.println("flushed");

        SessionFactory sessionFactory = HibernatePrefetch.buildSessionFactory();

        Session s = sessionFactory.openSession();
        Transaction t = s.beginTransaction();

        Parent parent = new Parent();
        parent.setName("p");
        s.save(parent);

        for (int i = 0; i < 2; i++) {
            Child child = new Child();
            child.setName("child." + i);
            child.setParent(parent);
            s.save(child);

            // System.out.println("childs=" + parent.getChilds());

            for (int j = 0; j < 2; j++) {
                GrandChild grandchild = new GrandChild();
                grandchild.setName("grandchild." + i + "." + j);
                grandchild.setChild(child);
                s.save(grandchild);
            }
        }

        System.out.println("parent id = " + parent.getId());

        t.commit();
        s.close();

        s = sessionFactory.openSession();

        Parent p = (Parent) s.load(Parent.class, 1);
        for (Child c : p.getChilds()) {
            for (GrandChild gc : c.getGrandchilds()) {
                System.out.println(gc.getName());
            }
        }

        System.out.println("saved");
    }

}
