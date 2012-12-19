package joist.domain.orm.queries.columns;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;

public class DateAliasColumn<T extends DomainObject> extends AliasColumn<T, Date, Date> {

  public DateAliasColumn(Alias<T> alias, String name, Shim<T, Date> shim) {
    super(alias, name, shim);
  }

  @Override
  public Date toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getDate(i);
  }

}
