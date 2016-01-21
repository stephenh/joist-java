package features.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import features.domain.builders.Builders;
import features.domain.builders.ChildBuilder;
import features.domain.builders.ParentBuilder;
import joist.domain.orm.EagerLoading;
import joist.domain.uow.UoW;
import joist.jdbc.Jdbc;

public class ChildEagerLoadingTest extends AbstractFeaturesTest {

  private ParentBuilder p1;
  private ParentBuilder p2;

  @Before
  @Override
  public void setUp() {
    super.setUp();
    this.p1 = Builders.aParent().name("p1");
    this.p2 = Builders.aParent().name("p2");
    this.commitAndReOpen();
  }

  @Test
  public void testEagerLoadingIsEnabledByDefault() {
    // should probably not be enabled by default ... we'll see what happens
    Builders.aChild().name("c1").parent(this.p1);
    Builders.aChild().name("c2").parent(this.p2);
    this.commitAndReOpen();

    this.p1.get(); // reload each parent
    this.p2.get();
    Jdbc.resetStats();
    this.p1.get().getChilds(); // now reload each set of children
    this.p2.get().getChilds();
    Assert.assertEquals(1, Jdbc.numberOfQueries());
  }

  @Test
  public void testEagerLoadingWhenDisabled() {
    boolean wasEnabled = EagerLoading.isEnabled();
    try {
      EagerLoading.setEnabled(false);

      Builders.aChild().name("c1").parent(this.p1);
      Builders.aChild().name("c2").parent(this.p2);
      this.commitAndReOpen();

      this.p1.get(); // reload each parent
      this.p2.get();
      Jdbc.resetStats();
      this.p1.get().getChilds(); // now reload each set of children
      this.p2.get().getChilds(); // causes n+1 type behavior
      Assert.assertEquals(2, Jdbc.numberOfQueries());
    } finally {
      EagerLoading.setEnabled(wasEnabled);
    }
  }

  @Test
  public void testEagerLoadingWhenDisabledForJustOneInstance() {
    Builders.aChild().name("c1").parent(this.p1);
    ChildBuilder c2 = Builders.aChild().name("c2").parent(this.p2);
    Builders.aGrandChild().name("g3").child(c2).defaults();
    this.commitAndReOpen();

    this.p1.get(); // reload each parent
    this.p2.get();
    Jdbc.resetStats();
    UoW.getEagerCache().doNotEagerlyLoadFor(this.p2.get());
    this.p1.get().getChilds(); // now reload each set of children
    this.p2.get().getChilds(); // causes n+1 type behavior
    Assert.assertEquals(2, Jdbc.numberOfQueries());
    Assert.assertEquals(1, c2.grandChilds().size()); // other collections still work
  }

  @Test
  public void testEagerLoadingIsEnabledUsesOneQueryForAllChildren() {
    Builders.aChild().name("c1").parent(this.p1);
    Builders.aChild().name("c2").parent(this.p2);
    this.commitAndReOpen();

    this.p1.get(); // reload each parent
    this.p2.get();
    Jdbc.resetStats();
    this.p1.get().getChilds(); // now reload each set of children
    this.p2.get().getChilds();
    Assert.assertEquals(1, Jdbc.numberOfQueries());
  }

  @Test
  public void testEagerLoadingIsEnabledAndRemembersIfAParentDoesNotHaveAnyChildren() {
    Builders.aChild().name("c1").parent(this.p1);
    // Builders.aChild().name("c2").parent(this.p2); no child for p2
    this.commitAndReOpen();

    this.p1.get(); // reload each parent
    this.p2.get();
    Jdbc.resetStats();
    this.p1.get().getChilds(); // now reload each set of children
    this.p2.get().getChilds();
    Assert.assertEquals(1, Jdbc.numberOfQueries());
  }

  @Test
  public void testEagerLoadingIsEnabledAndReQueriesForNewlyLoadedParents() {
    Builders.aChild().name("c1").parent(this.p1);
    Builders.aChild().name("c2").parent(this.p2);
    this.commitAndReOpen();

    Jdbc.resetStats();
    this.p1.get(); // reload only p1
    this.p1.get().getChilds();
    // now reload p2
    this.p2.get();
    // and ensure we can get its children
    Assert.assertEquals(1, this.p2.childs().size());
    // even though it took an extra query (p1, c1, p2, c2)
    Assert.assertEquals(4, Jdbc.numberOfQueries());
  }

  @Test
  public void testEagerLoadingOfParents() {
    ChildBuilder c1 = Builders.aChild().name("c1").parent(this.p1);
    ChildBuilder c2 = Builders.aChild().name("c2").parent(this.p2);
    this.commitAndReOpen();

    c1.get(); // reload each child
    c2.get();
    Jdbc.resetStats();
    c1.parent(); // now reload each parent
    c2.parent();
    Assert.assertEquals(1, Jdbc.numberOfQueries());
  }

  @Test
  public void testEagerLoadingOfParentsWhenDisabled() {
    boolean wasEnabled = EagerLoading.isEnabled();
    try {
      EagerLoading.setEnabled(false);
      ChildBuilder c1 = Builders.aChild().name("c1").parent(this.p1);
      ChildBuilder c2 = Builders.aChild().name("c2").parent(this.p2);
      this.commitAndReOpen();

      c1.get(); // reload each child
      c2.get();
      Jdbc.resetStats();
      c1.parent(); // now reload each parent
      c2.parent();
      Assert.assertEquals(2, Jdbc.numberOfQueries());
    } finally {
      EagerLoading.setEnabled(wasEnabled);
    }
  }

  @Test
  public void testEagerLoadingOfParentsForNewlyLoadedChildren() {
    ChildBuilder c1 = Builders.aChild().name("c1").parent(this.p1);
    ChildBuilder c2 = Builders.aChild().name("c2").parent(this.p2);
    this.commitAndReOpen();

    c1.get(); // reload only c1
    Jdbc.resetStats();
    c1.parent(); // now reload c1's parent
    Assert.assertEquals(1, Jdbc.numberOfQueries());

    c2.get(); // now bring c2 into the UoW
    Jdbc.resetStats();
    c2.parent(); // getting c2's parent will need another query
    Assert.assertEquals(1, Jdbc.numberOfQueries());
  }

  @Test
  public void testEagerLoadingDoesNotFailIfDisconnected() {
    Parent p1 = this.p1.get();
    this.commitAndReOpen();
    try {
      // p1 is disconnected now (UoW is open, but p1 is not in it)
      Assert.assertEquals(0, p1.getChilds().size());
      Assert.fail();
    } catch (IllegalStateException ise) {
      Assert.assertEquals("Instance has been disconnected from the UoW: Parent[1]", ise.getMessage());
    }
  }

}
