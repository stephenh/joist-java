package joist.domain.orm.queries;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.util.Join;
import joist.util.Wrap;

/**
 * Joins a child/many (E) to a parent/one (N).
 *
 * @param E the child domain object
 * @param N the parent domain object
 */
public class JoinClause<E extends DomainObject, N extends DomainObject> {

  private final String type;
  private String text;
  private final Alias<?> alias;
  private Where additionalJoinCriterion;

  // For adding parent N as E.n_id = N.id, e.g.:
  // - q.join(n.on(e.n));
  // - INNER JOIN \"n\" n ON e.n_id = n.id
  public JoinClause(String type, Alias<N> newAlias, ForeignKeyAliasColumn<E, N> on) {
    this(//
      newAlias,
      type,
      newAlias.getTableName(),
      newAlias.getName(),
      on.getQualifiedName(),
      (newAlias.getSubClassIdColumn() == null ? newAlias.getIdColumn() : newAlias.getSubClassIdColumn()).getQualifiedName());
  }

  // For adding child E as N.id = E.n_id, e.g.:
  // - INNER JOIN \"e\" e ON n.id = e.n_id
  // - join(e.n.on(n))
  public JoinClause(String type, Alias<E> newAlias, IdAliasColumn<? super N> existingIdColumn, ForeignKeyAliasColumn<E, N> newForeignKeyColumn) {
    this(newAlias, type, newAlias.getTableName(), newAlias.getName(), existingIdColumn.getQualifiedName(), newForeignKeyColumn.getQualifiedName());
  }

  /** Wild cards for utility code. */
  public JoinClause(String type, Alias<?> newAlias, IdAliasColumn<?> existingAliasIdColum, IdAliasColumn<?> newAliasIdColumn) {
    this(newAlias, type, newAlias.getTableName(), newAlias.getName(), existingAliasIdColum.getQualifiedName(), newAliasIdColumn.getQualifiedName());
  }

  public JoinClause<E, N> and(Where additionalJoinCriterion) {
    this.additionalJoinCriterion = additionalJoinCriterion;
    this.text += " AND " + additionalJoinCriterion.getSql();
    return this;
  }

  private JoinClause(Alias<?> alias, String type, String tableName, String aliasName, String existingColumn, String newColumn) {
    this.text = Join.space(type, Wrap.quotes(tableName), aliasName, "ON", existingColumn, "=", newColumn);
    this.alias = alias;
    this.type = type;
  }

  public String getType() {
    return this.type;
  }

  public String toString() {
    return this.text;
  }

  public Alias<?> getAlias() {
    return this.alias;
  }

  public Where getAdditionalJoinCriterion() {
    return this.additionalJoinCriterion;
  }
}
