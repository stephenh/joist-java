package joist.domain.orm.queries;

import joist.domain.orm.queries.columns.ColumnExpression;

public class SelectItem {

  private final String text;
  private final ColumnExpression<?, ?> column;
  private final String as;

  public SelectItem(ColumnExpression<?, ?> column) {
    this.column = column;
    this.text = column.getQualifiedName();
    this.as = null;
  }

  public SelectItem(ColumnExpression<?, ?> column, String as) {
    this.column = column;
    this.text = column.getQualifiedName() + " as " + as;
    this.as = as;
  }

  public SelectItem(String text, String as) {
    this.column = null;
    this.text = text + " as " + as;
    this.as = as;
  }

  protected SelectItem(ColumnExpression<?, ?> column, String text, String as) {
    this.column = column;
    this.text = text;
    this.as = as;
  }

  public String toString() {
    return this.text;
  }

  public ColumnExpression<?, ?> getColumn() {
    return this.column;
  }

  public String getAs() {
    return this.as;
  }

}
