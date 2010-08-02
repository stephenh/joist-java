package joist.domain.orm.queries.columns;

import joist.domain.Code;
import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

/**
 * @param T the domain object the column is within
 * @param W the domain object the column points to
 */
public class CodeAliasColumn<T extends DomainObject, W extends Code> extends AliasColumn<T, Integer, Integer> {

  public CodeAliasColumn(Alias<T> alias, String name, Shim<T, Integer> shim) {
    super(alias, name, shim);
  }

  public Where eq(W value) {
    return new Where(this.getQualifiedName() + " = ?", value.getId());
  }

}
