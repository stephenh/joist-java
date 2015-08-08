package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.Update;
import joist.util.Copy;

import org.junit.Assert;
import org.junit.Test;

import features.Registry;
import features.domain.AbstractFeaturesTest;
import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public class PrimitivesQueryTest extends AbstractFeaturesTest {

  @Test
  public void testFindForIdEqualsSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.id.eq(1));
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id = ?", q.toSql());
    Assert.assertEquals(Copy.list(1), q.getParameters());
  }

  @Test
  public void testFindForNameEqualsSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.name.eq("bob"));
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.name = ?", q.toSql());
    Assert.assertEquals(Copy.list("bob"), q.getParameters());
  }

  @Test
  public void testFindForNameEqualsOrderByNameSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.name.eq("bob"));
    q.orderBy(p.name.asc());
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.name = ?\n ORDER BY p.name", q.toSql());
    Assert.assertEquals(Copy.list("bob"), q.getParameters());
  }

  @Test
  public void testFindForIdLessThanMoreThanSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.id.lessThan(10l).and(p.id.greaterThan(1l)));
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id < ?\nAND p.id > ?", q.toSql());
    Assert.assertEquals(Copy.list(10l, 1l), q.getParameters());
  }

  @Test
  public void testFindForNameDtoSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.name.as("name"));
    Assert.assertEquals("SELECT p.name as name\n FROM \"primitives\" p", q.toSql());
  }

  @Test
  public void testUpdateSql() {
    List<Integer> ids = Copy.list(1, 2, 3);

    PrimitivesAlias p = new PrimitivesAlias("p");
    Update<Primitives> q = Update.into(p);
    q.set(p.flag.to(true));
    q.where(p.id.in(ids));
    Assert.assertEquals("UPDATE \"primitives\"\n SET \"flag\" = ?\n WHERE id in (1,2,3)", q.toSql());
    Object booleanTrue = Registry.getRepository().getDb().isMySQL() ? 1 : true;
    Assert.assertEquals(Copy.list(booleanTrue), this.toParameters(q));
  }

  private List<Object> toParameters(Update<Primitives> q) {
    return q.getParameters();
  }

}
