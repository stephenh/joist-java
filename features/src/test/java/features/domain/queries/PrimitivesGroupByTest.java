package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;
import junit.framework.Assert;
import features.domain.AbstractFeaturesTest;
import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public class PrimitivesGroupByTest extends AbstractFeaturesTest {

  public void testGroupByWithOneResult() {
    new Primitives("p1");
    new Primitives("p2");
    this.commitAndReOpen();

    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.flag.as("flag"));
    q.groupBy(p.flag);
    List<ByFlag> l = q.list(ByFlag.class);
    Assert.assertEquals(1, l.size());
    Assert.assertEquals(false, l.get(0).flag.booleanValue());
  }

  public void testGroupByWithTwoResults() {
    new Primitives("p1").flag(true);
    new Primitives("p2").flag(false);
    this.commitAndReOpen();

    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.flag.as("flag"));
    q.groupBy(p.flag);
    q.orderBy(p.flag.asc());
    List<ByFlag> l = q.list(ByFlag.class);
    Assert.assertEquals(2, l.size()); // now two
    Assert.assertEquals(false, l.get(0).flag.booleanValue());
    Assert.assertEquals(true, l.get(1).flag.booleanValue());
  }

  public void testHavingByWithTwoResultsAndOneFiltered() {
    new Primitives("p1").flag(true);
    new Primitives("p2").flag(false);
    new Primitives("p3").flag(false);
    this.commitAndReOpen();

    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.flag.as("flag"));
    q.groupBy(p.flag);
    q.having(q.count.greatherThan(1));
    List<ByFlag> l = q.list(ByFlag.class);
    Assert.assertEquals(1, l.size()); // back to one
    Assert.assertEquals(false, l.get(0).flag.booleanValue());
  }

  public static class ByFlag {
    public Boolean flag;
    public Long count;
  }
}
