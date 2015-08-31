package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.JoinClause;
import joist.domain.orm.queries.Where;
import joist.util.Join;

/**
 * @param T the domain object the column is within
 * @param W the domain object the column points to
 */
public class ForeignKeyAliasColumn<T extends DomainObject, W extends DomainObject> extends AliasColumn<T, Long, Long> {

  public ForeignKeyAliasColumn(Alias<T> alias, String name, Shim<T, Long> shim) {
    super(alias, name, shim);
  }

  public Where eq(W value) {
    return new Where(this.getQualifiedName() + " = ?", value.getId());
  }

  public Where in(Collection<? extends Number> ids) {
    return new Where(this.getQualifiedName() + " in (" + Join.comma(ids) + ")");
  }

  public Where eq(Integer value) {
    return new Where(this.getQualifiedName() + " = ?", value);
  }

  public JoinClause<T, W> on(Alias<W> on) {
    return new JoinClause<T, W>("INNER JOIN", this.getAlias(), on.getIdColumn(), this);
  }

  public JoinClause<T, W> leftOn(Alias<W> on) {
    return new JoinClause<T, W>("LEFT JOIN", this.getAlias(), on.getIdColumn(), this);
  }

  @Override
  public Long toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getLong(i);
  }

}
