package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.orm.queries.SelectItem;

/**
 * @param IN the jdbc property type for input to the function
 * @param OUT the domain property type for the output of the function
 */
public abstract class Aggregate<IN, OUT> extends ColumnExpression<IN, OUT> {

  private final String function;
  private final String qualifiedName;

  protected Aggregate(String function) {
    this(function, null);
  }

  protected Aggregate(String function, AliasColumn<?, ?, ?> column) {
    this.function = function;
    if (column == null) {
      this.qualifiedName = this.function + "(*)";
    } else {
      this.qualifiedName = this.function + "(" + column.getQualifiedName() + ")";
    }
  }

  public static Aggregate<Long, Long> count() {
    return new Count();
  }

  public static <T> Aggregate<Long, Long> count(AliasColumn<?, ?, ?> column) {
    return new Count(column);
  }

  public static <T> Aggregate<T, T> sum(AliasColumn<?, T, T> column) {
    return new Sum<T>(column);
  }

  @Override
  public String getQualifiedName() {
    return this.qualifiedName;
  }

  public SelectItem as(String as) {
    return new SelectItem(this, as);
  }

  private static class Sum<T> extends Aggregate<T, T> {
    private final AliasColumn<?, T, T> column;

    private Sum(AliasColumn<?, T, T> column) {
      super("sum", column);
      this.column = column;
    }

    @Override
    public T toJdbcValue(ResultSet rs, int i) throws SQLException {
      return this.column.toJdbcValue(rs, i);
    }

    @Override
    public T toJdbcValue(T domainValue) {
      return domainValue;
    }
  }

  private static class Count extends Aggregate<Long, Long> {
    private Count(AliasColumn<?, ?, ?> column) {
      super("count", column);
    }

    private Count() {
      super("count", null);
    }

    @Override
    public Long toJdbcValue(ResultSet rs, int i) throws SQLException {
      return rs.getLong(i);
    }

    @Override
    public Long toJdbcValue(Long domainValue) {
      return domainValue;
    }
  }
}
