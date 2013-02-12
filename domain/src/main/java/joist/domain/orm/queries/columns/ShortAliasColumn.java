package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

public class ShortAliasColumn<T extends DomainObject> extends AliasColumn<T, Short, Integer> {

  public ShortAliasColumn(Alias<T> alias, String name, Shim<T, Short> shim) {
    super(alias, name, shim);
  }

  public Short toDomainValue(Integer jdbcValue) {
    return (jdbcValue == null) ? null : jdbcValue.shortValue();
  }

  public Integer toJdbcValue(Short domainValue) {
    return (domainValue == null) ? null : domainValue.intValue();
  }

  public Where moreThan(Short value) {
    return new Where(this.getQualifiedName() + " > ?", value);
  }

  public Where lessThan(Short value) {
    return new Where(this.getQualifiedName() + " < ?", value);
  }

  @Override
  public Integer toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getInt(i);
  }

}
