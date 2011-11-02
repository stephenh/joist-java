package features.domain;

import java.util.List;

import joist.domain.uow.UoW;

import org.junit.Assert;
import org.junit.Test;

public class OuterJoinFetchingTest extends AbstractFeaturesTest {

  @Test
  public void testWithoutFetching() {
    Parent p = new Parent("parent");
    new Child(p, "child1");
    this.commitAndReOpen();

    List<Parent> l = Parent.queries.findWithoutChildFetch();
    Assert.assertEquals(1, l.size());
    Assert.assertEquals(1, UoW.getIdentityMap().size());
  }

  @Test
  public void testWithFetching() {
    Parent p = new Parent("parent");
    new Child(p, "child1");
    this.commitAndReOpen();

    List<Parent> l = Parent.queries.findWithChildFetch();
    Assert.assertEquals(1, l.size());
    // We already have both objects
    // Assert.assertEquals(2, UoW.getIdentityMap().size());
  }

}
