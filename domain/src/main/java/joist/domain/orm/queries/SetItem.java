package joist.domain.orm.queries;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.AliasColumn;

public class SetItem<T extends DomainObject> {

  private final AliasColumn<T, ?, ?> column;
  private final Object value;

  public SetItem(AliasColumn<T, ?, ?> column, Object value) {
    this.column = column;
    this.value = value;
  }

  public AliasColumn<T, ?, ?> getColumn() {
    return this.column;
  }

  public Object getValue() {
    return this.value;
  }

  public String toString() {
    return this.column.getQualifiedName();
  }

}
