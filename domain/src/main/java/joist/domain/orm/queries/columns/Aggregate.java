package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.orm.queries.SelectItem;

/**
 * @param DOMAIN the domain property type for the result of the function
 * @param JDBC the jdbc property type the result of the function
 */
public abstract class Aggregate<DOMAIN, JDBC> extends ColumnExpression<DOMAIN, JDBC> {

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

  public static Aggregate<Long, Long> count(AliasColumn<?, ?, ?> column) {
    return new Count(column);
  }

  public static <DOMAIN, JDBC> Aggregate<DOMAIN, JDBC> average(AliasColumn<?, DOMAIN, JDBC> column) {
    return new GenericAggregate<DOMAIN, JDBC>("avg", column);
  }

  public static <DOMAIN, JDBC> Aggregate<DOMAIN, JDBC> min(AliasColumn<?, DOMAIN, JDBC> column) {
    return new GenericAggregate<DOMAIN, JDBC>("min", column);
  }

  public static <DOMAIN, JDBC> Aggregate<DOMAIN, JDBC> max(AliasColumn<?, DOMAIN, JDBC> column) {
    return new GenericAggregate<DOMAIN, JDBC>("max", column);
  }

  public static <DOMAIN, JDBC> Aggregate<DOMAIN, JDBC> sum(AliasColumn<?, DOMAIN, JDBC> column) {
    return new GenericAggregate<DOMAIN, JDBC>("sum", column);
  }

  @Override
  public String getQualifiedName() {
    return this.qualifiedName;
  }

  public SelectItem as(String as) {
    return new SelectItem(this, as);
  }

  private static class GenericAggregate<DOMAIN, JDBC> extends Aggregate<DOMAIN, JDBC> {
    private final AliasColumn<?, DOMAIN, JDBC> column;

    private GenericAggregate(String keyword, AliasColumn<?, DOMAIN, JDBC> column) {
      super(keyword, column);
      this.column = column;
    }

    @Override
    public JDBC toJdbcValue(ResultSet rs, int i) throws SQLException {
      return this.column.toJdbcValue(rs, i);
    }

    public DOMAIN toDomainValue(JDBC v) {
      return this.column.toDomainValue(v);
    }

    @Override
    public JDBC toJdbcValue(DOMAIN domainValue) {
      return this.column.toJdbcValue(domainValue);
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
