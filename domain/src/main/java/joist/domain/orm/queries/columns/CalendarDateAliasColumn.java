package joist.domain.orm.queries.columns;

import java.sql.Date;
import java.util.TimeZone;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.TimePoint;

public class CalendarDateAliasColumn<T extends DomainObject> extends AliasColumn<T, CalendarDate, Date> {

  public CalendarDateAliasColumn(Alias<T> alias, String name, Shim<T, CalendarDate> shim) {
    super(alias, name, shim);
  }

  @Override
  public CalendarDate toDomainValue(Date jdbcValue) {
    if (jdbcValue == null) {
      return null;
    }
    return CalendarDate.from(TimePoint.from(jdbcValue), TimeZone.getDefault());
  }

  @Override
  public Date toJdbcValue(CalendarDate domainValue) {
    if (domainValue == null) {
      return null;
    }
    return new Date(domainValue.startAsTimePoint(TimeZone.getDefault()).asJavaUtilDate().getTime());
  }

}
