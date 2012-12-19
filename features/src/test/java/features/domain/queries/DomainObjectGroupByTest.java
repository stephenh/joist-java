package features.domain.queries;

import static features.domain.builders.Builders.aChild;

import java.util.List;

import joist.domain.orm.queries.Select;

import org.junit.Assert;
import org.junit.Test;

import features.domain.AbstractFeaturesTest;
import features.domain.Child;
import features.domain.ChildAlias;
import features.domain.builders.ChildBuilder;

public class DomainObjectGroupByTest extends AbstractFeaturesTest {

  @Test
  public void testGroupByWithOneResult() {
    ChildBuilder child = aChild().defaults();
    aChild().with(child.parent()).defaults();
    this.commitAndReOpen();

    ChildAlias c = new ChildAlias("c");
    Select<Child> q = Select.from(c);
    q.select(c.parent.as("parentId"));
    q.groupBy(c.parent);
    List<ByParent> l = q.list(ByParent.class);
    Assert.assertEquals(1, l.size());
    Assert.assertEquals(1L, l.get(0).parentId.longValue());
  }

  @Test
  public void testGroupByWithTwoResults() {
    aChild().defaults();
    aChild().defaults();
    this.commitAndReOpen();

    ChildAlias c = new ChildAlias("c");
    Select<Child> q = Select.from(c);
    q.select(c.parent.as("parentId"));
    q.groupBy(c.parent);
    List<ByParent> l = q.list(ByParent.class);

    Assert.assertEquals(2, l.size()); // now two
    Assert.assertEquals(1L, l.get(0).parentId.longValue());
    Assert.assertEquals(2L, l.get(1).parentId.longValue());
  }

  @Test
  public void testHavingWithTwoResultsAndOneFiltered() {
    ChildBuilder child = aChild().defaults();
    aChild().with(child.parent()).defaults();
    aChild().defaults();
    this.commitAndReOpen();

    ChildAlias c = new ChildAlias("c");
    Select<Child> q = Select.from(c);
    q.select(c.parent.as("parentId"));
    q.groupBy(c.parent);
    q.having(q.count.greatherThan(1));
    List<ByParent> l = q.list(ByParent.class);

    Assert.assertEquals(1, l.size()); // back to one
    Assert.assertEquals(1L, l.get(0).parentId.longValue());
  }

  public static class ByParent {
    public Long parentId;
    public Long count;
  }
}
