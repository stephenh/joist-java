package features.domain;

import java.util.Date;

import junit.framework.Assert;

import com.domainlanguage.money.Money;
import com.domainlanguage.time.TimePoint;

public class PrimitivesCTest extends AbstractFeaturesTest {

  public void testMoneyAndTimestamp() {
    PrimitivesC p = new PrimitivesC();
    p.setName("foo");
    p.setDollarAmount(Money.dollars(2.34));
    p.setTimestamp(TimePoint.from(new Date()));
    this.commitAndReOpen();

    long count = PrimitivesC.queries.numberOver230();
    Assert.assertEquals(1, count);

    p = PrimitivesC.queries.find(1);
    Assert.assertNotNull(p.getTimestamp());
  }
}
