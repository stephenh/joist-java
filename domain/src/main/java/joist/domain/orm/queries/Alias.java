package joist.domain.orm.queries;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.domain.orm.queries.columns.LongAliasColumn;

/**
 * The base class that the code-generated, type-safe aliases extend.
 *
 * @param T the domain class to query against
 */
public abstract class Alias<T extends DomainObject> {

  private final Class<T> domainClass;
  private final String tableName;
  private final String name;
  private final List<Alias<? extends T>> subClassAliases = new ArrayList<Alias<? extends T>>();

  protected Alias(Class<T> domainClass, String tableName, String name) {
    this.domainClass = domainClass;
    this.tableName = tableName;
    this.name = name;
  }

  public abstract List<AliasColumn<T, ?, ?>> getColumns();

  public abstract IdAliasColumn<? super T> getIdColumn();

  public abstract LongAliasColumn<? super T> getVersionColumn();

  public abstract IdAliasColumn<T> getSubClassIdColumn();

  public abstract int getOrder();

  public String getTableName() {
    return this.tableName;
  }

  public String getName() {
    return this.name;
  }

  protected void addSubClassAlias(Alias<? extends T> subClassAlias) {
    this.subClassAliases.add(subClassAlias);
  }

  public List<Alias<? extends T>> getSubClassAliases() {
    return this.subClassAliases;
  }

  /** @return the current domain class
   *
   * In a 3-level inheritance hierarchy, this would be the 3rd level.
   */
  public Class<T> getDomainClass() {
    return this.domainClass;
  }

  /** @return the root domain class--never null
   *
   * In a 3-level inheritance hierarchy, this would be the 1st level.
   */
  public Class<? super T> getDomainRootClass() {
    Alias<? super T> current = this;
    while (current.getBaseClassAlias() != null) {
      current = current.getBaseClassAlias();
    }
    return current.getDomainClass();
  }

  /** @return the immediate super domain alias--could be null
   *
   * In a 3-level inheritance hierarchy, this would be the 2nd level.
   */
  public Alias<? super T> getBaseClassAlias() {
    return null;
  }

  /** @return the root domain alias--never null
   *
   * In a 3-level inheritance hierarchy, this would be the 1st level.
   */
  public final Alias<? super T> getRootClassAlias() {
    Alias<? super T> current = this;
    while (current.getBaseClassAlias() != null) {
      current = current.getBaseClassAlias();
    }
    return current;
  }

  public boolean isRootClass() {
    return this.getBaseClassAlias() == null;
  }

}
