package features.domain;

import joist.domain.orm.queries.Select;
import joist.util.Copy;
import joist.util.Join;
import junit.framework.Assert;
import junit.framework.TestCase;

public class InheritanceAQueryTest extends TestCase {

    public void testShimWorksWithSubClasses() {
        InheritanceASubOne a = new InheritanceASubOne();

        a.setName("base");
        Assert.assertEquals("base", InheritanceABaseCodegen.Shims.name.get(a));

        InheritanceABaseCodegen.Shims.name.set(a, "change");
        Assert.assertEquals("change", a.getName());
    }

    public void testFindBaseAlsoFindsSub() {
        // SELECT b.id, b.name, b.version, sa.one, sb.two
        // FROM inheritance_a b
        // LEFT OUTER JOIN inheritance_a_sub_one sa ON b.id = sa.id
        // LEFT OUTER JOIN inheritance_a_sub_two sb ON b.id = sb.id
        // WHERE b.name = 'b'

        InheritanceABaseAlias b = new InheritanceABaseAlias("b");
        Select<InheritanceABase> q = Select.from(b);
        q.where(b.name.equals("b"));

        Assert.assertEquals(Join.lines(
            "SELECT b.id, b.name, b.version, b_0.one, b_1.two,"
                + " CASE WHEN b_1.id IS NOT NULL THEN 1 WHEN b_0.id IS NOT NULL THEN 0 ELSE -1 END as _clazz",
            " FROM `inheritance_a_base` b",
            " LEFT OUTER JOIN `inheritance_a_sub_one` b_0 ON b.id = b_0.id",
            " LEFT OUTER JOIN `inheritance_a_sub_two` b_1 ON b.id = b_1.id",
            " WHERE b.name = ?"), q.toSql());
        Assert.assertEquals(Copy.list("b"), q.getWhere().getParameters());
    }

    public void testFindSubOnelsoFindsBase() {
        InheritanceASubOneAlias sa = new InheritanceASubOneAlias("sa");
        Select<InheritanceASubOne> q = Select.from(sa);
        q.where(sa.one.equals("one"));

        Assert.assertEquals(Join.lines(
            "SELECT sa.one, sa_b.id, sa_b.name, sa_b.version",
            " FROM `inheritance_a_sub_one` sa",
            " INNER JOIN `inheritance_a_base` sa_b ON sa.id = sa_b.id",
            " WHERE sa.one = ?"), q.toSql());
        Assert.assertEquals(Copy.list("one"), q.getWhere().getParameters());
    }
}
