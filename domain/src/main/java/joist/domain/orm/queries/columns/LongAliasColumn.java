package joist.domain.orm.queries.columns;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

public class LongAliasColumn<T extends DomainObject> extends AliasColumn<T, Long, Long> {

  public LongAliasColumn(Alias<T> alias, String name, Shim<T, Long> shim) {
    super(alias, name, shim);
  }

  public Where moreThan(Long value) {
    return new Where(this.getQualifiedName() + " > ?", value);
  }

  public Where lessThan(Long value) {
    return new Where(this.getQualifiedName() + " < ?", value);
  }

}