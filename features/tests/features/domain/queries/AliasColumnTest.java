package features.domain.queries;

import joist.domain.orm.queries.Select;
import joist.util.Copy;
import junit.framework.Assert;
import junit.framework.TestCase;
import features.domain.Primitives;
import features.domain.PrimitivesAlias;

public class AliasColumnTest extends TestCase {

    public void testIsNull() {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.id.isNull());
        Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id IS NULL", q.toSql());
        Assert.assertEquals(Copy.list(), q.getParameters());
    }

    public void testIsNotNull() {
        PrimitivesAlias p = new PrimitivesAlias("p");
        Select<Primitives> q = Select.from(p);
        q.where(p.id.isNotNull());
        Assert.assertEquals("SELECT p.flag, p.id, p.name, p.version\n FROM \"primitives\" p\n WHERE p.id IS NOT NULL", q.toSql());
        Assert.assertEquals(Copy.list(), q.getParameters());
    }

}
