package joist.domain;

import joist.domain.orm.NamedUpdater;
import joist.domain.orm.Repository;
import joist.domain.uow.UoW;
import joist.util.TestCounters;

public abstract class AbstractDomainObjectsTest {

  protected static Repository repo;

  public static void setRepository(Repository repo) {
    AbstractDomainObjectsTest.repo = repo;
  }

  // leave off annotation so subclasses can opt in
  protected void setUp() {
    TestCounters.resetAll();
    // Protect against previous tests that didn't clean up
    if (UoW.isOpen()) {
      UoW.close();
    }
    UoW.open(repo, new NamedUpdater("testing"));
  }

  // leave off annotation so subclasses can opt in
  protected void tearDown() {
    if (UoW.isOpen()) {
      UoW.close();
    }
  }

  protected void commitAndReOpen() {
    UoW.commitAndReOpen();
  }

  protected void rollback() {
    UoW.rollback();
  }

  protected void flush() {
    UoW.flush();
  }

  @SuppressWarnings("unchecked")
  protected <T extends DomainObject> T reload(T instance) {
    return (T) UoW.load(instance.getClass(), instance.getId());
  }

}
