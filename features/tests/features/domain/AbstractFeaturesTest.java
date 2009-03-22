package features.domain;

import java.sql.Connection;

import joist.domain.AbstractDomainObjectsTest;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;

import org.h2.engine.Session;
import org.h2.jdbc.JdbcConnection;
import org.h2.schema.Schema;

import features.Registry;
import features.cli.JoistCli;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

    static {
        Registry.start();
        JoistCli c = new JoistCli();
        c.migrateDatabase();
    }

    public void setUp() throws Exception {
        super.setUp();

        Connection c = Registry.getDataSource().getConnection();
        Session s = (Session) ((JdbcConnection) c).getSession();
        Schema pub = s.getDatabase().getSchema("PUBLIC");
        try {
            for (Alias<?> a : AliasRegistry.getAliases()) {
                pub.getTableOrView(s, a.getTableName()).truncate(s);
                if (a.isRootClass()) {
                    pub.getSequence((a.getTableName() + "_id_seq").toUpperCase()).setStartValue(1);
                }
            }
        } finally {
            c.close();
        }
    }

}
