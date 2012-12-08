package features.domain;

import joist.domain.orm.queries.Select;
import joist.util.Copy;
import joist.util.Join;

import org.junit.Assert;
import org.junit.Test;

public class InheritanceAQueryTest {

  @Test
  public void testShimWorksWithSubClasses() {
    InheritanceASubOne a = new InheritanceASubOne();

    a.setName("base");
    Assert.assertEquals("base", InheritanceABaseCodegen.Shims.name.get(a));

    InheritanceABaseCodegen.Shims.name.set(a, "change");
    Assert.assertEquals("change", a.getName());
  }

  @Test
  public void testFindBaseAlsoFindsSub() {
    // SELECT b.id, b.name, b.version, sa.one, sb.two
    // FROM inheritance_a b
    // LEFT OUTER JOIN inheritance_a_sub_one sa ON b.id = sa.id
    // LEFT OUTER JOIN inheritance_a_sub_two sb ON b.id = sb.id
    // WHERE b.name = 'b'

    InheritanceABaseAlias b = new InheritanceABaseAlias("b");
    Select<InheritanceABase> q = Select.from(b);
    q.where(b.name.eq("b"));

    Assert.assertEquals(Join.lines(
      "SELECT"
        + " b.id, b.name, b.version, b.inheritance_a_owner_id,"
        + " b_0.one, b_0.inheritance_a_thing_id,"
        + " b_1.two, b_1.inheritance_a_thing_id,"
        + " CASE WHEN b_1.id IS NOT NULL THEN 1 WHEN b_0.id IS NOT NULL THEN 0 ELSE -1 END as _clazz",
      " FROM \"inheritance_a_base\" b",
      " LEFT OUTER JOIN \"inheritance_a_sub_one\" b_0 ON b.id = b_0.id",
      " LEFT OUTER JOIN \"inheritance_a_sub_two\" b_1 ON b.id = b_1.id",
      " WHERE b.name = ?"), q.toSql());
    Assert.assertEquals(Copy.list("b"), q.getWhere().getParameters());
  }

  @Test
  public void testFindSubOneAlsoFindsBase() {
    InheritanceASubOneAlias sa = new InheritanceASubOneAlias("sa");
    Select<InheritanceASubOne> q = Select.from(sa);
    q.where(sa.one.eq("one"));

    Assert.assertEquals(Join.lines(//
      "SELECT"//
        + " sa_b.id, sa_b.name, sa_b.version, sa_b.inheritance_a_owner_id,"
        + " sa.one, sa.inheritance_a_thing_id",
      " FROM \"inheritance_a_sub_one\" sa",
      " INNER JOIN \"inheritance_a_base\" sa_b ON sa.id = sa_b.id",
      " WHERE sa.one = ?"), q.toSql());
    Assert.assertEquals(Copy.list("one"), q.getWhere().getParameters());
  }

  @Test
  public void testFindSubOneWithFilterOnBase() {
    InheritanceASubOneAlias sa = new InheritanceASubOneAlias("sa");
    Select<InheritanceASubOne> q = Select.from(sa);
    q.where(sa.inheritanceAOwner.eq(1L));

    Assert.assertEquals(Join.lines(//
      "SELECT"//
        + " sa_b.id, sa_b.name, sa_b.version, sa_b.inheritance_a_owner_id,"
        + " sa.one, sa.inheritance_a_thing_id",
      " FROM \"inheritance_a_sub_one\" sa",
      " INNER JOIN \"inheritance_a_base\" sa_b ON sa.id = sa_b.id",
      " WHERE sa_b.inheritance_a_owner_id = ?"), q.toSql());
    Assert.assertEquals(Copy.list(1L), q.getWhere().getParameters());
  }
}
