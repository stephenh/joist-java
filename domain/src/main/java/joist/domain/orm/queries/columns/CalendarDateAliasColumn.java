package joist.domain.orm.queries.columns;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

import com.domainlanguage.time.CalendarDate;
import com.domainlanguage.time.CalendarInterval;
import com.domainlanguage.time.TimePoint;

public class CalendarDateAliasColumn<T extends DomainObject> extends AliasColumn<T, CalendarDate, Date> {

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
    return CalendarDate.from(TimePoint.from(jdbcValue), TimeZone.getDefault());
  }

  @Override
  public Date toJdbcValue(CalendarDate domainValue) {
    if (domainValue == null) {
      return null;
    }
    return new Date(domainValue.startAsTimePoint(TimeZone.getDefault()).asJavaUtilDate().getTime());
  }

  @Override
  public void setJdbcValue(T instance, ResultSet rs, int i) throws SQLException {
    this.setJdbcValue(instance, rs.getDate(i));
  }

}
