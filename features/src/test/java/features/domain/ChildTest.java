package features.domain;

import static features.domain.builders.Builders.aParent;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import joist.domain.ValidationAssert;
import joist.domain.orm.IdentityMap;
import joist.domain.uow.UoW;
import joist.domain.validation.ValidationException;
import joist.jdbc.Jdbc;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.ParentBuilder;

public class ChildTest extends AbstractFeaturesTest {

  @Test
  public void testNotNull() {
    Child c = new Child();
    ValidationAssert.assertErrors(c, "Name is required", "Parent is required");
  }

  @Test
  public void testNotEmpty() {
    Child c = new Child();
    c.setName("");
    ValidationAssert.assertErrors(c, "Name cannot be empty", "Parent is required");
  }

  @Test
  public void testParentChanged() {
    Child c = new Child();
    Parent p = new Parent();
    c.setParent(p);
    assertThat(c.getChanged().getOriginalParent(), is(nullValue()));
    c.setParent(null);
    // still the null value because it really is the original for this instance
    assertThat(c.getChanged().getOriginalParent(), is(nullValue()));
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

  @Test
  public void testChildIsNotDeletedByDefaultWhenAssociatedByParent() {
    Parent p = new Parent("p");
    new Child(p, "name");
    this.commitAndReOpen();

    p = this.reload(p);
    p.setChilds(new ArrayList<Child>());
    try {
      this.commitAndReOpen();
      fail();
    } catch (ValidationException ve) {
      assertThat(ve.getMessage(), is("New validation errors - Parent is required - Child[1]"));
    }
  }

  @Test
  public void testChildIsImplicitlyDeletedWhenEnabledAndAssociatedByParent() {
    Parent p = new Parent("p");
    new Child(p, "name");
    this.commitAndReOpen();

    p = this.reload(p);
    UoW.enableImplicitDeletionOfChildren();
    p.setChilds(new ArrayList<Child>());
    this.commitAndReOpen();
    Assert.assertEquals(0, Child.queries.count());
    // test setting a different child
  }

  @Test
  public void testChildIsReownedAfterBeingImplicitlyDeleted() {
    Parent p = new Parent("p");
    Child c = new Child(p, "name");
    this.commitAndReOpen();
    Assert.assertEquals(1, Child.queries.count());

    p = this.reload(p);
    c = this.reload(c);
    UoW.enableImplicitDeletionOfChildren();
    p.setChilds(new ArrayList<Child>());
    // now make a new parent for the child
    Parent p2 = new Parent("p2");
    c.setParent(p2);
    this.commitAndReOpen();
    // the child was saved from being deleted
    Assert.assertEquals(1, Child.queries.count());
  }

  @Test
  public void testAddingAChildTicksTheParent() {
    Parent p = new Parent("parent");
    this.commitAndReOpen();
    Assert.assertEquals(0, p.getVersion().intValue());

    p = this.reload(p);
    new Child(p, "child1");
    this.commitAndReOpen();
    Assert.assertEquals(1, p.getVersion().intValue());
  }

  @Test
  public void testLoadUsesALimit() {
    int oldSizeLimit = IdentityMap.getSizeLimit();
    try {
      IdentityMap.setSizeLimit(10);
      ParentBuilder p = aParent().defaults();
      this.commitAndReOpen();
      for (int i = 0; i < 10; i++) {
        Jdbc.update(UoW.getConnection(), "INSERT INTO child (parent_id, name, version) VALUES ({}, {}, 0);", p.id(), i);
      }
      this.commitAndReOpen();
      try {
        p.childs();
        fail();
      } catch (IllegalStateException ise) {
        assertThat(ise.getMessage(), is("IdentityMap grew over the 10 instance limit"));
      }
    } finally {
      IdentityMap.setSizeLimit(oldSizeLimit);
    }
  }

  @Test
  public void testQueryThatJoinsWithChildren() {
    Parent p = new Parent("p");
    new Child(p, "c1");
    new Child(p, "c2");
    this.commitAndReOpen();
    List<Parent> ps = Parent.queries.findChildrenStartingWith("c");
    assertThat(ps.size(), is(1));
    assertThat(ps.get(0).getChilds().size(), is(2));
  }

  @Test
  public void testCustomOneToManyNames() {
    ParentI p = new ParentI();
    p.addChildA(new ChildIA());
    p.setChildB(new ChildIB());
    this.commitAndReOpen();
    p = this.reload(p);
    assertThat(p.getChildAs().size(), is(1));
    assertThat(p.getChildB(), is(not(nullValue())));
  }
}
