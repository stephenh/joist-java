package features.domain;

import joist.domain.AbstractDomainObjectsTest;
import joist.domain.FlushTestDatabase;
import features.Registry;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

  static {
    System.setProperty("joist.util.jdbc.trackStats", "true");
    System.setProperty("log4j.configuration", "log4j-test.properties");
    Registry.start();
  }

  public void setUp() throws Exception {
    super.setUp();
    FlushTestDatabase.execute();
  }

}
