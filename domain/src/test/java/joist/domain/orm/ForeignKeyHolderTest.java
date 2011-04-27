package joist.domain.orm;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ForeignKeyHolderTest extends TestCase {

  public void testLoadOutsideUoWFails() {
    ForeignKeyHolder<DummyDomainObject, DummyDomainObject> h = new ForeignKeyHolder<DummyDomainObject, DummyDomainObject>(
      DummyDomainObject.class,
      DummyDomainObject.class,
      null,
      null);
    h.setId(1l);
    try {
      h.get();
      Assert.fail();
    } catch (RuntimeException e) {
      Assert.assertEquals("The UoW is currently closed", e.getMessage());
    }
  }

}
