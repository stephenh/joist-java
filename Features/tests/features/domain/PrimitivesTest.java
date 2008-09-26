package features.domain;

import junit.framework.Assert;
import features.domain.Primitives;
import features.domain.mappers.PrimitivesMapper;

public class PrimitivesTest extends AbstractFeaturesTest {

    public void testSave() {
        Primitives foo = new Primitives();
        foo.setName("testSave");
        this.commit();
        Assert.assertEquals(2, foo.getId().intValue());

        Primitives reloaded = new PrimitivesMapper().find(2);
        Assert.assertEquals(2, reloaded.getId().intValue());
        Assert.assertEquals("testSave", reloaded.getName());
    }

    public void testFlushMeansWeCanFindItRightAway() {
        Primitives foo = new Primitives();
        foo.setName("testSave");
        this.flush();

        Primitives reloaded = new PrimitivesMapper().find(2);
        Assert.assertEquals("testSave", reloaded.getName());
    }

    public void testRollbackAfterFlushMeansItIsNotThere() {
        Primitives foo = new Primitives();
        foo.setName("testSave");
        this.flush();
        this.rollback();

        try {
            new PrimitivesMapper().find(2);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Not found", e.getMessage());
        }
    }

    public void testSaveTwoInSameUowGetDifferentIds() {
        new Primitives().setName("foo");
        new Primitives().setName("bar");
        this.commit();
        Assert.assertEquals(2, new PrimitivesMapper().findByName("foo").getId().intValue());
        Assert.assertEquals(3, new PrimitivesMapper().findByName("bar").getId().intValue());
    }

    public void testLoadViaIdTwiceReturnsTheSameInstance() {
        new Primitives().setName("foo");
        this.commit();
        Primitives twp1 = new PrimitivesMapper().find(2);
        Primitives twp2 = new PrimitivesMapper().find(2);
        Assert.assertTrue(twp1 == twp2);
    }

    public void testLoadViaIdThenNameReturnsTheSameInstance() {
        new Primitives().setName("foo");
        this.commit();
        Primitives twp1 = new PrimitivesMapper().find(2);
        Primitives twp2 = new PrimitivesMapper().findByName("foo");
        Assert.assertTrue(twp1 == twp2);
    }

    public void testUpdateTicksVersion() {
        new Primitives().setName("foo");
        this.commit();

        Primitives twp = new PrimitivesMapper().find(2);
        Assert.assertEquals(0, twp.getVersion().intValue());
        twp.setName("bar");
        this.commit();
        // Should already see it tick
        Assert.assertEquals(1, twp.getVersion().intValue());

        // And after reloading
        twp = new PrimitivesMapper().find(2);
        Assert.assertEquals(1, twp.getVersion().intValue());
    }

}
