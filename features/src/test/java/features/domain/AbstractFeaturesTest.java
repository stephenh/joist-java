package features.domain;

import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;

import features.Registry;
import joist.domain.AbstractDomainObjectsTest;
import joist.domain.FlushTestDatabase;
import joist.domain.orm.Db;
import joist.domain.orm.queries.columns.CalendarDateAliasColumn;
import joist.jdbc.Jdbc;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

  static {
    System.setProperty(Jdbc.trackStatsKey, "true");
    System.setProperty("log4j.configuration", "log4j-test.properties");
    Registry.start();
    setRepository(Registry.getRepository());
    // Our MySQL settings tell MySQL to always use UTC
    if (Registry.getRepository().getDb() == Db.MYSQL) {
      CalendarDateAliasColumn.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
  }

  protected static final Db db = Registry.getRepository().getDb();

  @Before
  @Override
  public void setUp() {
    super.setUp();
    FlushTestDatabase.execute(Registry.getRepository());
  }

  @After
  public void tearDown() {
    super.tearDown();
  }

  protected boolean isMySql() {
    return Registry.getRepository().getDb().isMySQL();
  }

}
