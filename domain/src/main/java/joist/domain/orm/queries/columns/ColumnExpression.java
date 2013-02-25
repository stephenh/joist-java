package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.orm.queries.Where;

/**
 * @param U the domain property type
 * @param V the jdbc property type
 */
public abstract class ColumnExpression<U, V> {

  public abstract V toJdbcValue(ResultSet rs, int i) throws SQLException;

  public U toDomainValue(V jdbcValue) {
    return (U) jdbcValue;
  }

  public U toDomainValue(ResultSet rs, int i) throws SQLException {
    return this.toDomainValue(this.toJdbcValue(rs, i));
  }

  public abstract String getQualifiedName();

  public Where lessThanOrEqual(U value) {
    return new Where(this.getQualifiedName() + " <= ?", this.toJdbcValue(value));
  }

  public Where lessThan(U value) {
    return new Where(this.getQualifiedName() + " < ?", this.toJdbcValue(value));
  }

  public Where greaterThanOrEqual(U value) {
    return new Where(this.getQualifiedName() + " >= ?", this.toJdbcValue(value));
  }

  public Where greaterThan(U value) {
    return new Where(this.getQualifiedName() + " > ?", this.toJdbcValue(value));
  }

  public Where lessThanOrEqual(AliasColumn<?, U, V> value) {
    return new Where(this.getQualifiedName() + " <= " + value.getQualifiedName());
  }

  public Where lessThan(AliasColumn<?, U, V> value) {
    return new Where(this.getQualifiedName() + " < " + value.getQualifiedName());
  }

  public Where greaterThanOrEqual(AliasColumn<?, U, V> value) {
    return new Where(this.getQualifiedName() + " >= " + value.getQualifiedName());
  }

  public Where greaterThan(AliasColumn<?, U, V> value) {
    return new Where(this.getQualifiedName() + " > " + value.getQualifiedName());
  }

  /** @return a where for between {@code lower} and {@code upper}, inclusive. */
  public Where between(U lower, U upper) {
    return new Where(this.getQualifiedName() + " BETWEEN ? AND ?", this.toJdbcValue(lower), this.toJdbcValue(upper));
  }

  public abstract V toJdbcValue(U domainValue);
}
