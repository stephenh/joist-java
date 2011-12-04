package features.domain;

import joist.domain.AbstractDomainObjectsTest;
import joist.domain.FlushTestDatabase;

import org.junit.After;
import org.junit.Before;

import features.Registry;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

  static {
    System.setProperty("joist.util.jdbc.trackStats", "true");
    System.setProperty("log4j.configuration", "log4j-test.properties");
    Registry.start();
    setRepository(Registry.getRepository());
  }

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
}
