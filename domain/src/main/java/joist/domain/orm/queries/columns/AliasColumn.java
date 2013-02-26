package joist.domain.orm.queries.columns;

import java.sql.ResultSet;
import java.sql.SQLException;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Order;
import joist.domain.orm.queries.SelectItem;
import joist.domain.orm.queries.Where;

/**
 * @param T the domain object type
 * @param U the domain property type
 * @param V the jdbc property type
 */
public abstract class AliasColumn<T extends DomainObject, U, V> extends ColumnExpression<U, V> {

  private final Alias<T> alias;
  private final String name;
  private final Shim<T, U> shim;

  protected AliasColumn(Alias<T> alias, String name, Shim<T, U> shim) {
    this.alias = alias;
    this.name = name;
    this.shim = shim;
  }

  public U getDomainValue(T instance) {
    return this.shim.get(instance);
  }

  public V getJdbcValue(T instance) {
    return this.toJdbcValue(this.getDomainValue(instance));
  }

  public void setJdbcValue(T instance, V jdbcValue) {
    this.shim.set(instance, this.toDomainValue(jdbcValue));
  }

  public abstract V toJdbcValue(ResultSet rs, int i) throws SQLException;

  public V toJdbcValue(U domainValue) {
    return (V) domainValue;
  }

  public Order asc() {
    return new Order(this.getQualifiedName());
  }

  public Order desc() {
    return new Order(this.getQualifiedName() + " desc");
  }

  public Where eq(U value) {
    return new Where(this.getQualifiedName() + " = ?", this.toJdbcValue(value));
  }

  public Where isNull() {
    return new Where(this.getQualifiedName() + " IS NULL");
  }

  public Where isNotNull() {
    return new Where(this.getQualifiedName() + " IS NOT NULL");
  }

  public SelectItem as(String as) {
    return new SelectItem(this, as);
  }

  public String getQualifiedName() {
    return this.alias.getName() + "." + this.name;
  }

  public String getName() {
    return this.name;
  }

  public Alias<T> getAlias() {
    return this.alias;
  }

  public String toString() {
    return this.getQualifiedName();
  }

}
