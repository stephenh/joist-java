package features.domain.queries;

import java.util.List;

import joist.domain.orm.queries.Select;

import com.domainlanguage.money.Money;
import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.TimePoint;

import features.domain.PrimitivesC;
import features.domain.PrimitivesCAlias;

public class PrimitivesCQueries extends PrimitivesCQueriesCodegen {

  public List<CalendarDate> findDatesOnly() {
    PrimitivesCAlias p = new PrimitivesCAlias("p");
    Select<PrimitivesC> q = Select.from(p);
    q.select(p.day.as("day"));
    q.orderBy(p.day.asc());
    return q.listValues(CalendarDate.class);
  }

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
