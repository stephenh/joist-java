package features.domain;

import junit.framework.Assert;
import features.domain.mappers.PrimitivesMapper;

public class PrimitivesAggregatesTest extends AbstractFeaturesTest {

    public void testCount() {
        new Primitives("count");
        this.commit();
        Assert.assertEquals(1, new PrimitivesMapper().count());
    }

    public void testCountWithConditions() {
        new Primitives("count");
        this.commit();

        Assert.assertEquals(1, new PrimitivesMapper().countWhereFlagIs(false));
        Assert.assertEquals(0, new PrimitivesMapper().countWhereFlagIs(true));
    }

}
