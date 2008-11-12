package features.domain;

import java.util.List;

import junit.framework.Assert;
import features.domain.queries.Query;
import features.domain.queries.PrimitivesQueries.NameAndFlag;

public class PrimitivesAggregatesTest extends AbstractFeaturesTest {

    public void testCount() {
        new Primitives("count");
        this.commitAndReOpen();
        Assert.assertEquals(1, Query.primitives.count());
    }

    public void testCountWithConditions() {
        new Primitives("count");
        this.commitAndReOpen();

        Assert.assertEquals(1, Query.primitives.countWhereFlagIs(false));
        Assert.assertEquals(0, Query.primitives.countWhereFlagIs(true));
    }

    public void testNameOnly() {
        new Primitives("testNameOnly");
        this.commitAndReOpen();
        Assert.assertEquals("testNameOnly", Query.primitives.findNameOnly(2));
    }

    public void testNameAndFlagOnly() {
        new Primitives("name1");
        new Primitives("name2");
        this.commitAndReOpen();

        List<NameAndFlag> dtos = Query.primitives.findNameAndFlagOnly();
        Assert.assertEquals(2, dtos.size());
        Assert.assertEquals("name1", dtos.get(0).name);
        Assert.assertEquals("name2", dtos.get(1).name);
    }

}
