package features.domain;

import joist.domain.AbstractDomainObjectsTest;
import joist.domain.orm.Repository;
import joist.jdbc.Jdbc;
import features.Registry;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

  static {
    Registry.start();
  }

  public void setUp() throws Exception {
    super.setUp();
    if (Repository.db.isPg()) {
      Jdbc.queryForInt(Registry.getDataSource(), "select * from flush_test_database()");
    } else if (Repository.db.isMySQL()) {
      Jdbc.queryForInt(Registry.getDataSource(), "CALL flush_test_database()");
    } else {
      throw new IllegalStateException("Unhandled db " + Repository.db);
    }
  }

}
