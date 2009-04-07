package features.domain;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.AbstractDomainObjectsTest;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.jdbc.Jdbc;
import joist.jdbc.RowMapper;

import org.h2.engine.Session;
import org.h2.jdbc.JdbcConnection;
import org.h2.schema.Schema;

import com.vladium.utils.timing.ITimer;
import com.vladium.utils.timing.TimerFactory;

import features.Registry;
import features.cli.JoistCli;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

    private static boolean started = false;
    private static File backup = new File("./bin/backup.sql");

    public void setUp() throws Exception {
        if (!started) {
            Registry.start();

            if (backup.exists()) {
                ITimer t = TimerFactory.newTimer();
                t.start();

                Connection conn = Registry.getDataSource().getConnection();
                Jdbc.update(conn, "RUNSCRIPT FROM '{}'", backup.getAbsolutePath());
                conn.close();

                t.stop();
                System.out.println("Setup Took " + t.getDuration() + "ms");
            } else {
                ITimer t = TimerFactory.newTimer();
                t.start();

                new JoistCli().migrateDatabase();

                t.stop();
                System.out.println("Setup Took " + t.getDuration() + "ms");
                Connection conn = Registry.getDataSource().getConnection();
                Jdbc.query(conn, "SCRIPT TO '" + backup.getAbsolutePath() + "'", new RowMapper() {
                    public void mapRow(ResultSet rs) throws SQLException {
                        // System.out.println(rs.getObject(1));
                    }
                });
                conn.close();
            }
            started = true;
        }

        super.setUp();

        ITimer t = TimerFactory.newTimer();
        t.start();

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

        t.stop();
        System.out.println("Flush Took " + t.getDuration() + "ms");
    }

}
