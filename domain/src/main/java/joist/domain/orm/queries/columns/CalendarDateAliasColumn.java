package joist.domain.orm.queries.columns;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.CalendarInterval;
import com.domainlanguage.time.TimePoint;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

public class CalendarDateAliasColumn<T extends DomainObject> extends AliasColumn<T, CalendarDate, Date> {

  private static volatile TimeZone timeZone = TimeZone.getDefault();

  /**
   * Allows changing the time zone used to read/write days from the database.
   *
   * This is a global setting, so should be called in the application's initialization code.
   */
  public static void setTimeZone(TimeZone timeZone) {
    CalendarDateAliasColumn.timeZone = timeZone;
  }

  public CalendarDateAliasColumn(Alias<T> alias, String name, Shim<T, CalendarDate> shim) {
    super(alias, name, shim);
  }

  public Where within(CalendarInterval interval) {
    // should be on/after the start
    final Where startCond;
    if (interval.start() == null) {
      startCond = new Where("1 = 1");
    } else {
      String startOp = interval.includesLowerLimit() ? " >= ?" : " > ?";
      startCond = new Where(this.getQualifiedName() + startOp, this.toJdbcValue(interval.start()));
    }
    // should be on/before the end
    final Where endCond;
    if (interval.end() == null) {
      endCond = new Where("1 = 1");
    } else {
      String endOp = interval.includesUpperLimit() ? " <= ?" : " < ?";
      endCond = new Where(this.getQualifiedName() + endOp, this.toJdbcValue(interval.end()));
    }
    return startCond.and(endCond);
  }

  @Override
  public CalendarDate toDomainValue(Date jdbcValue) {
    if (jdbcValue == null) {
      return null;
    }
    return CalendarDate.from(TimePoint.from(jdbcValue), timeZone);
  }

  @Override
  public Date toJdbcValue(CalendarDate domainValue) {
    if (domainValue == null) {
      return null;
    }
    return new Date(domainValue.startAsTimePoint(timeZone).asJavaUtilDate().getTime());
  }

  @Override
  public Date toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getDate(i);
  }

}
