package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;
import joist.util.Join;

/**
 * @param T should always be the root class--I think so
 */
public class IdAliasColumn<T extends DomainObject> extends AliasColumn<T, Long, Long> {

  public IdAliasColumn(final Alias<T> alias, String name, Shim<T, Long> shim) {
    super(alias, name, shim);
  }

  public Where eq(Integer value) {
    return new Where(this.getQualifiedName() + " = ?", value);
  }

  public Where eq(T value) {
    return new Where(this.getQualifiedName() + " = ?", value.getId());
  }

  public Where in(Collection<? extends Number> ids) {
    return new Where(this.getQualifiedName() + " in (" + Join.comma(ids) + ")");
  }

  @Override
  public Long toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getLong(i);
  }

}
