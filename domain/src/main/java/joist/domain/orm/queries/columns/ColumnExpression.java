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

  public abstract String getQualifiedName();

  public Where lessThanOrEqual(U value) {
    return new Where(this.getQualifiedName() + " <= ?", value);
  }

  public Where lessThan(U value) {
    return new Where(this.getQualifiedName() + " < ?", value);
  }

  public Where greaterThanOrEqual(U value) {
    return new Where(this.getQualifiedName() + " >= ?", value);
  }

  public Where greaterThan(U value) {
    return new Where(this.getQualifiedName() + " > ?", value);
  }

  /** @return a where for between {@code lower} and {@code upper}, inclusive. */
  public Where between(U lower, U upper) {
    return new Where(this.getQualifiedName() + " BETWEEN ? AND ?", lower, upper);
  }

}
