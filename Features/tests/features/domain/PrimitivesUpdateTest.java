package features.domain;

import junit.framework.Assert;

import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.util.Copy;

import features.domain.mappers.PrimitivesMapper;

public class PrimitivesUpdateTest extends AbstractFeaturesTest {

    public void testChangeFlag() {
        new Primitives("testSave");
        this.commit();

        Assert.assertEquals(false, new PrimitivesMapper().find(2).getFlag());
        Ids<Primitives> ids = new Ids<Primitives>(Primitives.class, Copy.list(2));
        new PrimitivesMapper().setFlag(ids, true);
        this.commitAndReOpen();

        Assert.assertEquals(true, new PrimitivesMapper().find(2).getFlag());
    }

    public void testChangeFlagWithDynamicList() {
        new Primitives("foo1");
        new Primitives("foo2");
        new Primitives("bar");
        this.commitAndReOpen();

        Ids<Primitives> ids = new PrimitivesMapper().findIdsWithNameLike("foo%");
        Assert.assertEquals(2, ids.size());
        Assert.assertEquals(false, new PrimitivesMapper().find(2).getFlag());
        Assert.assertEquals(false, new PrimitivesMapper().find(3).getFlag());
        Assert.assertEquals(false, new PrimitivesMapper().find(4).getFlag());

        new PrimitivesMapper().setFlag(ids, true);
        this.commitAndReOpen();

        Assert.assertEquals(true, new PrimitivesMapper().find(2).getFlag());
        Assert.assertEquals(true, new PrimitivesMapper().find(3).getFlag());
        Assert.assertEquals(false, new PrimitivesMapper().find(4).getFlag());
    }

}
