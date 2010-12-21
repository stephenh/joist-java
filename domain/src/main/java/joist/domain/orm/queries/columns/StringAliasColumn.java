package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

public class StringAliasColumn<T extends DomainObject> extends AliasColumn<T, String, String> {

  public StringAliasColumn(Alias<T> alias, String name, Shim<T, String> shim) {
    super(alias, name, shim);
  }

  public Where like(String pattern) {
    return new Where(this.getQualifiedName() + " like ?", pattern);
  }

  @Override
  public void setJdbcValue(T instance, ResultSet rs, String name) throws SQLException {
    this.setJdbcValue(instance, rs.getString(name));
  }

}
