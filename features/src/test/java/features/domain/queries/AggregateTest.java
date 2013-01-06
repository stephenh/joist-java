package features.domain.queries;

import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.columns.Aggregate;
import joist.util.Copy;

import org.junit.Assert;
import org.junit.Test;

import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public class AggregateTest {

  @Test
  public void testSelectSum() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p).select(Aggregate.sum(p.id).as("sumOfIds"));
    Assert.assertEquals("SELECT sum(p.id) as sumOfIds\n FROM \"primitives\" p", q.toSql());
    Assert.assertEquals(Copy.list(), q.getParameters());
  }

  @Test
  public void testSelectCountStar() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p).select(Aggregate.count().as("countAll"));
    Assert.assertEquals("SELECT count(*) as countAll\n FROM \"primitives\" p", q.toSql());
    Assert.assertEquals(Copy.list(), q.getParameters());
  }

  @Test
  public void testSelectCountIds() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p).select(Aggregate.count(p.id).as("countOfIds"));
    Assert.assertEquals("SELECT count(p.id) as countOfIds\n FROM \"primitives\" p", q.toSql());
    Assert.assertEquals(Copy.list(), q.getParameters());
  }

  @Test
  public void testHavingGreaterThan() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p).select(Aggregate.count().as("count"));
    q.groupBy(p.name);
    q.having(Aggregate.count().greaterThan(1L));
    Assert.assertEquals("SELECT count(*) as count\n FROM \"primitives\" p\n GROUP BY p.name\n HAVING count(*) > ?", q.toSql());
    Assert.assertEquals(Copy.list(1L), q.getParameters());
  }
}
