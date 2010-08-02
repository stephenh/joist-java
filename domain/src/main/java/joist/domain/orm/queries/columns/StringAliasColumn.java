package joist.domain.orm.queries.columns;

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

}
