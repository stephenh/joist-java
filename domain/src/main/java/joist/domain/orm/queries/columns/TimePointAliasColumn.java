package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;

import com.domainlanguage.time.TimePoint;

public class TimePointAliasColumn<T extends DomainObject> extends AliasColumn<T, TimePoint, Timestamp> {

  public TimePointAliasColumn(Alias<T> alias, String name, Shim<T, TimePoint> shim) {
    super(alias, name, shim);
  }

  @Override
  public TimePoint toDomainValue(Timestamp jdbcValue) {
    return jdbcValue == null ? null : TimePoint.from(jdbcValue);
  }

  @Override
  public Timestamp toJdbcValue(TimePoint domainValue) {
    return domainValue == null ? null : new Timestamp(domainValue.asJavaUtilDate().getTime());
  }

  @Override
  public Timestamp toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getTimestamp(i);
  }

}
