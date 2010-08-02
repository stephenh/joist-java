package joist.domain.orm;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ForeignKeyHolderTest extends TestCase {

  public void testLoadOutsideUoWFails() {
    ForeignKeyHolder<DummyDomainObject> h = new ForeignKeyHolder<DummyDomainObject>(DummyDomainObject.class);
    h.setId(1);
    try {
      h.get();
      Assert.fail();
    } catch (RuntimeException e) {
      Assert.assertEquals("The UoW is currently closed", e.getMessage());
    }
  }

}
