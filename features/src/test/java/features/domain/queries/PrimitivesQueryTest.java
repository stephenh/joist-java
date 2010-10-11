package features.domain.queries;

import java.util.List;

import joist.domain.orm.Repository;
import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.Update;
import joist.util.Copy;
import junit.framework.Assert;
import features.domain.AbstractFeaturesTest;
import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public class PrimitivesQueryTest extends AbstractFeaturesTest {

  public void testFindForIdEqualsSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.id.eq(1));
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id = ?", q.toSql());
    Assert.assertEquals(Copy.list(1), q.getParameters());
  }

  public void testFindForNameEqualsSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.name.eq("bob"));
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.name = ?", q.toSql());
    Assert.assertEquals(Copy.list("bob"), q.getParameters());
  }

  public void testFindForNameEqualsOrderByNameSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.name.eq("bob"));
    q.orderBy(p.name.asc());
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.name = ?\n ORDER BY p.name", q.toSql());
    Assert.assertEquals(Copy.list("bob"), q.getParameters());
  }

  public void testFindForIdLessThanMoreThanSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.where(p.id.lessThan(10).and(p.id.greaterThan(1)));
    Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id < ?\n AND p.id > ?", q.toSql());
    Assert.assertEquals(Copy.list(10, 1), q.getParameters());
  }

  public void testFindForNameDtoSql() {
    PrimitivesAlias p = new PrimitivesAlias("p");
    Select<Primitives> q = Select.from(p);
    q.select(p.name.as("name"));
    Assert.assertEquals("SELECT p.name as name\n FROM \"primitives\" p", q.toSql());
  }

  public void testUpdateSql() {
    List<Integer> ids = Copy.list(1, 2, 3);

    PrimitivesAlias p = new PrimitivesAlias("p");
    Update<Primitives> q = Update.into(p);
    q.set(p.flag.to(true));
    q.where(p.id.in(ids));
    Assert.assertEquals("UPDATE \"primitives\"\n SET \"flag\" = ?\n WHERE id in (1,2,3)", q.toSql());
    Object booleanTrue = Repository.db.isMySQL() ? 1 : true;
    Assert.assertEquals(Copy.list(booleanTrue), this.toParameters(q));
  }

  private List<Object> toParameters(Update<Primitives> q) {
    return q.getParameters();
  }

}
