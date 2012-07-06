package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;
import joist.util.Join;

public class IntAliasColumn<T extends DomainObject> extends AliasColumn<T, Integer, Integer> {

  public IntAliasColumn(Alias<T> alias, String name, Shim<T, Integer> shim) {
    super(alias, name, shim);
  }

  public Where in(List<Integer> ids) {
    return new Where(this.getQualifiedName() + " in (" + Join.comma(ids) + ")");
  }

  @Override
  public void setJdbcValue(T instance, ResultSet rs, int i) throws SQLException {
    this.setJdbcValue(instance, rs.getInt(i));
  }

}
