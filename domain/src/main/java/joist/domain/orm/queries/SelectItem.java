package joist.domain.orm.queries;

import joist.domain.orm.queries.columns.AliasColumn;

public class SelectItem {

  private final String text;
  private final AliasColumn<?, ?, ?> column;
  private final String as;

  public SelectItem(AliasColumn<?, ?, ?> column) {
    this.column = column;
    this.text = column.getQualifiedName();
    this.as = null;
  }

  public SelectItem(AliasColumn<?, ?, ?> column, String as) {
    this.column = column;
    this.text = column.getQualifiedName() + " as " + as;
    this.as = as;
  }

  public SelectItem(String text, String as) {
    this.column = null;
    this.text = text + " as " + as;
    this.as = as;
  }

  public String toString() {
    return this.text;
  }

  public AliasColumn<?, ?, ?> getColumn() {
    return this.column;
  }

  public String getAs() {
    return this.as;
  }

}
