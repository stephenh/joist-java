package features.domain.queries;

import joist.domain.queries.Select;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Join;

import features.domain.Child;
import features.domain.ChildAlias;
import features.domain.ParentAlias;

public class ChildQueryTest extends TestCase {

    public void testFindForParentNameSql() {
        // SELECT * FROM child c
        // INNER JOIN parent p ON c.parent_id = p.id
        // WHERE p.name = 'bob'
        // ORDER BY p.name, c.name

        ChildAlias c = new ChildAlias("c");
        ParentAlias p = new ParentAlias("p");

        Select<Child> q = Select.from(c);
        q.join(p.on(c.parent));
        q.where(p.name.equals("bob"));
        q.orderBy(p.name.asc(), c.name.asc());

        Assert.assertEquals(Join.lines(
            "SELECT c.id, c.name, c.version, c.parent_id",
            " FROM \"child\" c",
            " INNER JOIN \"parent\" p ON c.parent_id = p.id",
            " WHERE p.name = ?",
            " ORDER BY p.name, c.name"), q.toSql());
        Assert.assertEquals(Copy.list("bob"), q.getWhere().getParameters());
    }

}
