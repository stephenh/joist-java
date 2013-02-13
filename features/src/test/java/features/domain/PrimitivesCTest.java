package features.domain;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.domainlanguage.money.Money;
import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.Duration;
import com.domainlanguage.time.TimePoint;

public class PrimitivesCTest extends AbstractFeaturesTest {

  @Test
  public void testMoneyAndTimestamp() {
    PrimitivesC p = new PrimitivesC();
    p.setName("foo");
    p.setDollarAmount(Money.dollars(2.34));
    p.setTimestamp(TimePoint.from(new Date()));
    p.setDay(CalendarDate.from(2000, 1, 1));
    this.commitAndReOpen();
  }

  @Test
  public void testQueryMoney() {
    PrimitivesC p = new PrimitivesC();
    p.setName("foo");
    p.setDollarAmount(Money.dollars(2.34));
    p.setTimestamp(TimePoint.from(new Date()));
    p.setDay(CalendarDate.from(2000, 1, 1));
    this.commitAndReOpen();
    long count = PrimitivesC.queries.numberWithAmountOver(Money.dollars(2.30));
    Assert.assertEquals(1, count);
  }

  @Test
  public void testQueryTimestamp() {
    PrimitivesC p = new PrimitivesC();
    p.setName("foo");
    p.setDollarAmount(Money.dollars(2.34));
    p.setTimestamp(TimePoint.from(new Date()));
    p.setDay(CalendarDate.from(2000, 1, 1));
    this.commitAndReOpen();
    long count = PrimitivesC.queries.numberWithTimepointAfter(p.getTimestamp().minus(Duration.seconds(1)));
    Assert.assertEquals(1, count);
  }

  @Test
  public void testQueryCalendarDate() {
    PrimitivesC p = new PrimitivesC();
    p.setName("foo");
    p.setDollarAmount(Money.dollars(2.34));
    p.setTimestamp(TimePoint.from(new Date()));
    p.setDay(CalendarDate.from(2000, 1, 1));
    this.commitAndReOpen();
    long count = PrimitivesC.queries.numberWithDayAfter(p.getDay().previousDay());
    Assert.assertEquals(1, count);
  }
}
