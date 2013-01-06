package features.domain.queries;

import static features.domain.builders.Builders.aPrimitivesB;

import java.util.List;

import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.columns.Aggregate;

import org.junit.Assert;
import org.junit.Test;

import features.domain.AbstractFeaturesTest;
import features.domain.Primitives;
import features.domain.PrimitivesAlias;
import features.domain.PrimitivesB;
import features.domain.PrimitivesBAlias;

public class PrimitivesGroupByTest extends AbstractFeaturesTest {

  @Test
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

  @Test
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

  @Test
  public void testHavingWithTwoResultsAndOneFiltered() {
    new Primitives("p1").flag(true);
    new Primitives("p2").flag(false);
    new Primitives("p3").flag(false);
    this.commitAndReOpen();

    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.flag.as("flag"));
    q.groupBy(p.flag);
    q.having(Aggregate.count().greaterThan(1L));
    List<ByFlag> l = q.list(ByFlag.class);
    Assert.assertEquals(1, l.size()); // back to one
    Assert.assertEquals(false, l.get(0).flag.booleanValue());
  }

  @Deprecated
  @Test
  public void testHavingWithTwoResultsAndOneFilteredWithDeprecatedSelectCountBuilder() {
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

  @Test
  public void testGroupByWithCountInSelect() {
    new Primitives("p1").flag(true);
    new Primitives("p2").flag(true);
    new Primitives("p3").flag(false);
    this.commitAndReOpen();

    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.flag.as("flag"), Aggregate.count().as("count"));
    q.groupBy(p.flag);
    q.orderBy(p.flag.asc());
    List<ByFlag> l = q.list(ByFlag.class);
    Assert.assertEquals(2, l.size()); // now two
    Assert.assertEquals(false, l.get(0).flag.booleanValue());
    Assert.assertEquals(1L, l.get(0).count.longValue());
    Assert.assertEquals(true, l.get(1).flag.booleanValue());
    Assert.assertEquals(2L, l.get(1).count.longValue());
  }

  @Deprecated
  @Test
  public void testGroupByWithCountInSelectWithDeprecatedSelectCountBuilder() {
    new Primitives("p1").flag(true);
    new Primitives("p2").flag(true);
    new Primitives("p3").flag(false);
    this.commitAndReOpen();

    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.flag.as("flag"), q.count.as("count"));
    q.groupBy(p.flag);
    q.orderBy(p.flag.asc());
    List<ByFlag> l = q.list(ByFlag.class);
    Assert.assertEquals(2, l.size()); // now two
    Assert.assertEquals(false, l.get(0).flag.booleanValue());
    Assert.assertEquals(1L, l.get(0).count.longValue());
    Assert.assertEquals(true, l.get(1).flag.booleanValue());
    Assert.assertEquals(2L, l.get(1).count.longValue());
  }

  @Test
  public void testGroupByWithSumInSelect() {
    aPrimitivesB().bool1(true).int1(10).defaults();
    aPrimitivesB().bool1(false).int1(20).defaults();
    aPrimitivesB().bool1(false).int1(30).defaults();
    this.commitAndReOpen();

    PrimitivesBAlias p = new PrimitivesBAlias("p");
    Select<PrimitivesB> q = Select.from(p);
    q.select(p.bool1.as("flag"), Aggregate.sum(p.int1).as("sum"));
    q.groupBy(p.bool1);
    q.orderBy(p.bool1.asc());
    List<ByFlag> l = q.list(ByFlag.class);
    Assert.assertEquals(2, l.size()); // now two
    Assert.assertEquals(false, l.get(0).flag.booleanValue());
    Assert.assertEquals(50, l.get(0).sum.intValue());
    Assert.assertEquals(true, l.get(1).flag.booleanValue());
    Assert.assertEquals(10, l.get(1).sum.intValue());
  }

  public static class ByFlag {
    public Boolean flag;
    public Long count;
    public Integer sum;
  }
}
