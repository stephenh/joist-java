package features.domain.queries;

import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.Where;
import joist.util.Copy;

import org.junit.Assert;
import org.junit.Test;

import features.domain.Primitives;
import features.domain.PrimitivesAlias;
import features.domain.PrimitivesB;
import features.domain.PrimitivesBAlias;

public class AliasColumnTest {

  @Test
  public void testIsNull() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.id.isNull());
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id IS NULL", q.toSql());
    Assert.assertEquals(Copy.list(), q.getParameters());
  }

  @Test
  public void testIsNotNull() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.id.isNotNull());
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id IS NOT NULL", q.toSql());
    Assert.assertEquals(Copy.list(), q.getParameters());
  }

  @Test
  public void testNot() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(Where.not(p.id.eq(1)));
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE NOT (\n p.id = ?\n)", q.toSql());
    Assert.assertEquals(Copy.list(1), q.getParameters());
  }

  @Test
  public void testLessThanColumnExpression() {
    PrimitivesBAlias p = new PrimitivesBAlias("p");
    Select<PrimitivesB> q = Select.from(p).select(p.id.as("id"), p.big1.as("big1"), p.big2.as("big2"));
    q.where(p.big1.lessThan(p.big2));
    Assert.assertEquals("SELECT p.id as id, p.big1 as big1, p.big2 as big2\n FROM \"primitives_b\" p\n WHERE p.big1 < p.big2", q.toSql());
    Assert.assertEquals(Copy.list(), q.getParameters());
  }

}
