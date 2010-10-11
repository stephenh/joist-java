package joist.domain.orm.queries.columns;

import java.util.List;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;
import joist.util.Join;

/**
 * @param T should always be the root class--I think so
 */
public class IdAliasColumn<T extends DomainObject> extends AliasColumn<T, Integer, Integer> {

  public IdAliasColumn(final Alias<T> alias, String name, Shim<T, Integer> shim) {
    super(alias, name, shim);
  }

  public Where eq(Integer value) {
    return new Where(this.getQualifiedName() + " = ?", value);
  }

  public Where eq(T value) {
    return new Where(this.getQualifiedName() + " = ?", value.getId());
  }

  public Where in(List<Integer> ids) {
    return new Where(this.getQualifiedName() + " in (" + Join.comma(ids) + ")");
  }

}
