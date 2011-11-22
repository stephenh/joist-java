package features.domain;

import java.util.List;

import joist.domain.ValidationAssert;

import org.junit.Assert;
import org.junit.Test;

public class ChildTest extends AbstractFeaturesTest {

  @Test
  public void testNotNull() {
    Child c = new Child();
    ValidationAssert.assertErrors(c, "Name is required", "Parent is required");
  }

  @Test
  public void testSaveAndReloadChildInSeparateUoWThanParent() {
    Parent p = new Parent();
    p.setName("parent");
    this.commitAndReOpen();

    Child c = new Child();
    c.setName("child");
    c.setParent(this.reload(p));
    this.commitAndReOpen();

    c = this.reload(c);
    p = this.reload(p);
    Assert.assertTrue(p == c.getParent());
  }

  @Test
  public void testSaveAndReloadChildInSameUoWAsParent() {
    Parent p = new Parent();
    p.setName("parent");

    Child c = new Child();
    c.setName("child");
    c.setParent(p);
    this.commitAndReOpen();

    c = this.reload(c);
    p = this.reload(p);
    Assert.assertTrue(p == c.getParent());
  }

  @Test
  public void testFindByParentName() {
    Parent p = new Parent();
    p.setName("parent");
    new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    List<Child> children = Child.queries.findForParentOfName("parent");
    Assert.assertEquals(2, children.size());
    Assert.assertEquals("child1", children.get(0).getName());
    Assert.assertEquals("child2", children.get(1).getName());
  }

  @Test
  public void testChangeOfParentIsSaved() {
    Parent p1 = new Parent("p1");
    new Child(p1, "child");
    this.commitAndReOpen();

    Child c = Child.queries.find(1);
    c.setParent(new Parent("p2"));
    Assert.assertTrue(c.getChanged().contains("parent"));
    this.commitAndReOpen();

    c = this.reload(c);
    Assert.assertEquals("p2", c.getParent().getName());
  }

  @Test
  public void testPercolationFromChildToParent() {
    Parent p = new Parent("p");
    Child c = new Child();
    c.setParent(p);
    Assert.assertEquals(1, p.getChilds().size());
    Assert.assertTrue(c.getChanged().contains("parent"));
    Assert.assertTrue(c.getChanged().hasParent());
    Assert.assertTrue(p.getChanged().contains("childs"));
    Assert.assertTrue(p.getChanged().hasChilds());

    c.setParent(null);
    Assert.assertEquals(0, p.getChilds().size());
    Assert.assertTrue(c.getChanged().contains("parent"));
    Assert.assertTrue(c.getChanged().hasParent());
    Assert.assertTrue(p.getChanged().contains("childs"));
    Assert.assertTrue(p.getChanged().hasChilds());
  }

  @Test
  public void testPercolationFromParentToChild() {
    Parent p = new Parent("p");
    Child c = new Child();
    p.addChild(c);
    Assert.assertEquals(p, c.getParent());
    Assert.assertTrue(c.getChanged().contains("parent"));
    Assert.assertTrue(c.getChanged().hasParent());
    Assert.assertTrue(p.getChanged().contains("childs"));
    Assert.assertTrue(p.getChanged().hasChilds());

    p.removeChild(c);
    Assert.assertEquals(null, c.getParent());
    Assert.assertTrue(c.getChanged().contains("parent"));
    Assert.assertTrue(c.getChanged().hasParent());
    Assert.assertTrue(p.getChanged().contains("childs"));
    Assert.assertTrue(p.getChanged().hasChilds());
  }

  @Test
  public void testParentIsRequired() {
    Child c = new Child();
    c.setName("child");
    ValidationAssert.assertErrors(c, "Parent is required");
  }

  @Test
  public void testChildCannotBeOrphaned() {
    Parent p = new Parent("p");
    Child c = new Child(p, "name");
    this.commitAndReOpen();

    // even though parent is our owner, simplying nulling it out will not lead to a delete
    c.setParent(null);
    ValidationAssert.assertErrors(c, "Parent is required");
  }

  @Test
  public void testChildIsDeletedWithParent() {
    Parent p = new Parent("p");
    new Child(p, "name");
    this.commitAndReOpen();
    Assert.assertEquals(1, Child.queries.count());

    p = this.reload(p);
    Parent.queries.delete(p);
    this.commitAndReOpen();
    Assert.assertEquals(0, Child.queries.count());
  }

}
