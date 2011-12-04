package joist.domain.orm;

import org.junit.Assert;
import org.junit.Test;

public class ForeignKeyListHolderTest {

  @Test
  public void testEagerlyLoadOutsideUoWWithNoIdIsOkay() {
    DummyDomainObject parent = new DummyDomainObject();
    ForeignKeyListHolder<DummyDomainObject, DummyDomainObject> h = //
    new ForeignKeyListHolder<DummyDomainObject, DummyDomainObject>(parent, null, null, null);
    // Worked because there is no id set
    Assert.assertEquals(0, h.get().size());
  }

  @Test
  public void testEagerlyLoadOutsideUoWWithNewIdIsOkay() {
    DummyDomainObject parent = new DummyDomainObject();
    ForeignKeyListHolder<DummyDomainObject, DummyDomainObject> h = //
    new ForeignKeyListHolder<DummyDomainObject, DummyDomainObject>(parent, null, null, null);
    parent.setId(1l);
    Assert.assertEquals(0, h.get().size());
  }

  @Test
  public void testEagerlyLoadOutsideUoWFails() {
    DummyDomainObject parent = new DummyDomainObject();
    ForeignKeyListHolder<DummyDomainObject, DummyDomainObject> h = //
    new ForeignKeyListHolder<DummyDomainObject, DummyDomainObject>(parent, null, null, null);
    parent.setId(1l);
    parent.getChanged().clear(); // don't want the id seen as changed
    try {
      h.get();
      Assert.fail();
    } catch (RuntimeException e) {
      Assert.assertEquals("The UoW is currently closed", e.getMessage());
    }
  }

  @Test
  public void testToString() {
    DummyDomainObject parent = new DummyDomainObject();
    ForeignKeyListHolder<DummyDomainObject, DummyDomainObject> h = //
    new ForeignKeyListHolder<DummyDomainObject, DummyDomainObject>(parent, null, null, null);
    Assert.assertEquals("unloaded + [] - []", h.toString());
    h.add(new DummyDomainObject());
    Assert.assertEquals("unloaded + [DummyDomainObject[null]] - []", h.toString());
  }

}
