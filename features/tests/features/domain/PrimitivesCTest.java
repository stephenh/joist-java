package features.domain;

import junit.framework.Assert;

import com.domainlanguage.money.Money;

public class PrimitivesCTest extends AbstractFeaturesTest {

    public void testMoney() {
        PrimitivesC p = new PrimitivesC();
        p.setName("foo");
        p.setDollarAmount(Money.dollars(2.34));
        this.commitAndReOpen();

        long count = PrimitivesC.queries.numberOver230();
        Assert.assertEquals(1, count);
    }
}
