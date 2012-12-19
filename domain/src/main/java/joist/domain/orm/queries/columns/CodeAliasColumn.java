package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.Code;
import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

/**
 * @param T the domain object the column is within
 * @param W the domain object the column points to
 */
public class CodeAliasColumn<T extends DomainObject, W extends Code> extends AliasColumn<T, Long, Long> {

  public CodeAliasColumn(Alias<T> alias, String name, Shim<T, Long> shim) {
    super(alias, name, shim);
  }

  public Where eq(W value) {
    return new Where(this.getQualifiedName() + " = ?", value.getId());
  }

  @Override
  public Long toJdbcValue(ResultSet rs, int i) throws SQLException {
    return rs.getLong(i);
  }

}
