package features.domain;

import junit.framework.Assert;

import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.util.Copy;

import features.domain.queries.Query;

public class PrimitivesUpdateTest extends AbstractFeaturesTest {

    public void testChangeFlag() {
        new Primitives("testSave");
        this.commitAndReOpen();

        Assert.assertEquals(false, Query.primitives.find(2).getFlag());
        Ids<Primitives> ids = new Ids<Primitives>(Primitives.class, Copy.list(2));
        Query.primitives.setFlag(ids, true);
        this.commitAndReOpen();

        Assert.assertEquals(true, Query.primitives.find(2).getFlag());
    }

    public void testChangeFlagWithDynamicList() {
        new Primitives("foo1");
        new Primitives("foo2");
        new Primitives("bar");
        this.commitAndReOpen();

        Ids<Primitives> ids = Query.primitives.findIdsWithNameLike("foo%");
        Assert.assertEquals(2, ids.size());
        Assert.assertEquals(false, Query.primitives.find(2).getFlag());
        Assert.assertEquals(false, Query.primitives.find(3).getFlag());
        Assert.assertEquals(false, Query.primitives.find(4).getFlag());

        Query.primitives.setFlag(ids, true);
        this.commitAndReOpen();

        Assert.assertEquals(true, Query.primitives.find(2).getFlag());
        Assert.assertEquals(true, Query.primitives.find(3).getFlag());
        Assert.assertEquals(false, Query.primitives.find(4).getFlag());
    }

}
