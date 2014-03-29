package features.domain;

import static joist.util.Copy.list;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Assert;
import org.junit.Test;

import features.domain.builders.Builders;
import features.domain.builders.ChildBuilder;
import features.domain.builders.ParentBuilder;

public class ParentTest extends AbstractFeaturesTest {

  @Test
  public void testLoadChildren() {
    Parent p = new Parent("parent");
    new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    p = Parent.queries.find(1);
    Assert.assertEquals(2, p.getChilds().size());
    Assert.assertEquals("child1", p.getChilds().get(0).getName());
    Assert.assertEquals("child2", p.getChilds().get(1).getName());

    Assert.assertTrue(p.getChilds() == p.getChilds());
  }

  @Test
  public void testChildrenArrayAndThenMapperLoadIsTheSame() {
    Parent p = new Parent("parent");
    new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    Assert.assertTrue(Parent.queries.find(1).getChilds().get(0) == Child.queries.find(1));
  }

  @Test
  public void testChildrenArrayAndAfterMapperLoadIsTheSame() {
    Parent p = new Parent("parent");
    new Child(p, "child1");
    new Child(p, "child2");
    this.commitAndReOpen();

    Assert.assertTrue(Child.queries.find(1) == Parent.queries.find(1).getChilds().get(0));
  }

  @Test
  public void testChildrenActsLikeASet() {
    Parent p = new Parent("parent");
    Assert.assertEquals(0, p.getChilds().size());
    Child c = new Child(p, "child1");
    // add again
    p.addChild(c);
    Assert.assertEquals(1, p.getChilds().size());
    this.commitAndReOpen();

    Assert.assertTrue(Parent.queries.find(1).getChilds().get(0) == Child.queries.find(1));
  }

  @Test
  public void testChildrenOrderIsKindOfRespected() {
    // given two children
    ParentBuilder p = Builders.aParent().defaults();
    ChildBuilder c1 = p.newChild().defaults();
    ChildBuilder c2 = p.newChild().defaults();
    this.commitAndReOpen();
    assertThat(p.child(0), is(c1));
    assertThat(p.child(1), is(c2));

    // the user can attempt to change the other
    p.get().setChilds(list(c2.get(), c1.get()));

    // and at least within that UoW it will look that way
    assertThat(p.child(0), is(c2));
    assertThat(p.child(1), is(c1));

    // but in the next UoW we revert to deterministic/by-id ordering
    this.commitAndReOpen();
    assertThat(p.child(0), is(c1));
    assertThat(p.child(1), is(c2));
  }

}
