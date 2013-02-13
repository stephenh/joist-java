package features.domain.queries;

import joist.domain.orm.queries.Select;

import com.domainlanguage.money.Money;
import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.TimePoint;

import features.domain.PrimitivesCAlias;

public class PrimitivesCQueries extends PrimitivesCQueriesCodegen {

  public long numberWithAmountOver(Money amount) {
    PrimitivesCAlias c = new PrimitivesCAlias("c");
    return Select.from(c).where(c.dollarAmount.greaterThan(amount)).count();
  }

  public long numberWithTimepointAfter(TimePoint point) {
    PrimitivesCAlias c = new PrimitivesCAlias("c");
    return Select.from(c).where(c.timestamp.greaterThan(point)).count();
  }

  public long numberWithDayAfter(CalendarDate day) {
    PrimitivesCAlias c = new PrimitivesCAlias("c");
    return Select.from(c).where(c.day.greaterThan(day)).count();
  }
}
