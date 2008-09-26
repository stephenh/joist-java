package features.domain;

import junit.framework.Assert;

import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.util.Copy;

import features.domain.mappers.PrimitivesMapper;

public class PrimitivesUpdateTest extends AbstractFeaturesTest {

    public void testSave() {
        new Primitives("testSave");
        this.commit();

        Assert.assertEquals(false, new PrimitivesMapper().find(2).getFlag());
        Ids<Primitives> ids = new Ids<Primitives>(Primitives.class, Copy.list(2));
        new PrimitivesMapper().setFlag(ids, true);
        this.commitAndReOpen();

        Assert.assertEquals(true, new PrimitivesMapper().find(2).getFlag());
    }

}
