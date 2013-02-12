package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.orm.queries.SelectItem;

/**
 * @param IN the jdbc property type for input to the function
 * @param OUT the jdbc property type for the output of the function
 */
public abstract class Aggregate<IN, OUT> extends ColumnExpression<IN, OUT> {

  private final String function;
  protected final AliasColumn<?, ?, IN> column;

  protected Aggregate(String function, AliasColumn<?, ?, IN> column) {
    this.function = function;
    this.column = column;
  }

  public static Aggregate<Object, Long> count() {
    return new Count<Object>();
  }

  public static <T> Aggregate<T, Long> count(AliasColumn<?, ?, T> column) {
    return new Count<T>(column);
  }

  public static <T> Aggregate<T, T> sum(AliasColumn<?, ?, T> column) {
    return new Sum<T>(column);
  }

  @Override
  public String getQualifiedName() {
    if (this.column == null) {
      return this.function + "(*)";
    }
    return this.function + "(" + this.column.getQualifiedName() + ")";
  }

  public SelectItem as(String as) {
    return new SelectItem(this, as);
  }

  private static class Sum<T> extends Aggregate<T, T> {
    private Sum(AliasColumn<?, ?, T> column) {
      super("sum", column);
    }

    @Override
    public T toJdbcValue(ResultSet rs, int i) throws SQLException {
      return this.column.toJdbcValue(rs, i);
    }
  }

  private static class Count<T> extends Aggregate<T, Long> {
    private Count(AliasColumn<?, ?, T> column) {
      super("count", column);
    }

    private Count() {
      super("count", null);
    }

    @Override
    public Long toJdbcValue(ResultSet rs, int i) throws SQLException {
      return rs.getLong(i);
    }
  }
}
