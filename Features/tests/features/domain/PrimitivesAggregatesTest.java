package features.domain;

import java.util.List;

import junit.framework.Assert;
import features.domain.mappers.PrimitivesMapper;
import features.domain.mappers.PrimitivesMapper.NameAndFlag;

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

    public void testNameOnly() {
        new Primitives("testNameOnly");
        this.commit();
        Assert.assertEquals("testNameOnly", new PrimitivesMapper().findNameOnly(2));
    }

    public void testNameAndFlagOnly() {
        new Primitives("name1");
        new Primitives("name2");
        this.commit();

        List<NameAndFlag> dtos = new PrimitivesMapper().findNameAndFlagOnly();
        Assert.assertEquals(2, dtos.size());
        Assert.assertEquals("name1", dtos.get(0).name);
        Assert.assertEquals("name2", dtos.get(1).name);
    }

}
