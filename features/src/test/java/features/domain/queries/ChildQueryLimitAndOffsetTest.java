package features.domain.queries;

import joist.domain.Requirements;
import joist.domain.orm.queries.Select;
import joist.util.Copy;
import joist.util.Join;
import junit.framework.Assert;
import junit.framework.TestCase;
import features.domain.Child;
import features.domain.ChildAlias;
import features.domain.ParentAlias;

public class ChildQueryLimitAndOffsetTest extends TestCase {

    public void testFindForParentNameSql() {
        Requirements.selectLimitAndOffset.tests();

        // SELECT * FROM child c
        // INNER JOIN parent p ON c.parent_id = p.id
        // WHERE p.name = 'bob'
        // ORDER BY p.name, c.name
        // LIMIT 10
        // OFFSET 20

        ChildAlias c = new ChildAlias("c");
        ParentAlias p = new ParentAlias("p");

        Select<Child> q = Select.from(c);
        q.join(p.on(c.parent));
        q.where(p.name.eq("bob"));
        q.orderBy(p.name.asc(), c.name.asc());
        q.limit(10);
        q.offset(20);

        Assert.assertEquals(Join.lines(
            "SELECT c.id, c.name, c.version, c.parent_id",
            " FROM \"child\" c",
            " INNER JOIN \"parent\" p ON c.parent_id = p.id",
            " WHERE p.name = ?",
            " ORDER BY p.name, c.name",
            " LIMIT 10",
            " OFFSET 20"), q.toSql());
        Assert.assertEquals(Copy.list("bob"), q.getWhere().getParameters());
    }

}
