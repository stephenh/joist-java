package features.domain;

import joist.domain.uow.UoW;

import org.junit.Assert;
import org.junit.Test;

public class ChildLazyListTest extends AbstractFeaturesTest {

  @Test
  public void testAddStillLazyLoadsList() {
    Parent p = new Parent("parent");
    new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    p = this.reload(p);
    new Child(p, "child3");
    // Should only have the parent so far
    Assert.assertEquals(1, UoW.getIdentityMap().size());
    // Now hit the db for the other 2
    Assert.assertEquals(3, p.getChilds().size());
  }

  @Test
  public void testRemoveStillLazyLoadsList() {
    Parent p = new Parent("parent");
    Child c1 = new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    p = this.reload(p);
    p.removeChild(this.reload(c1));
    // Should only have 2 objects
    Assert.assertEquals(2, UoW.getIdentityMap().size());
    // Now hit the db for the remaining object
    Assert.assertEquals(1, p.getChilds().size());
  }

  @Test
  public void testRemoveThenAddBeforeLoad() {
    Parent p = new Parent("parent");
    Child c1 = new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    p = this.reload(p);
    p.removeChild(this.reload(c1));
    p.addChild(this.reload(c1));
    // Should only have 2 objects
    Assert.assertEquals(2, UoW.getIdentityMap().size());
    // Now hit the db for the remaining object and don't dup c1
    Assert.assertEquals(2, p.getChilds().size());
  }

  @Test
  public void testAddThenRemoveBeforeLoad() {
    Parent p = new Parent("parent");
    new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    p = this.reload(p);
    Child c3 = new Child(p, "child3");
    p.removeChild(c3);
    // Should only have the parent so far
    Assert.assertEquals(1, UoW.getIdentityMap().size());
    // Now hit the db for the remaining object
    Assert.assertEquals(2, p.getChilds().size());
  }

  @Test
  public void testListIsReadOnly() {
    Parent p = new Parent("parent");
    try {
      p.getChilds().add(new Child());
      Assert.fail();
    } catch (UnsupportedOperationException uoe) {
      // pass
    }
  }

}
