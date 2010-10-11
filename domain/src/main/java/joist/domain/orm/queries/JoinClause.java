package joist.domain.orm.queries;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.orm.queries.columns.IdAliasColumn;
import joist.util.Join;
import joist.util.Wrap;

/**
 * Joins new W onto existing T.
 *
 * @param T existing domain object
 * @param W new domain object
 */
public class JoinClause<E extends DomainObject, N extends DomainObject> {

  private final String text;

  public JoinClause(String type, Alias<N> newAlias, ForeignKeyAliasColumn<E, N> on) {
    this(type, newAlias.getTableName(), newAlias.getName(), on.getQualifiedName(), newAlias.getIdColumn().getQualifiedName());
  }

  public JoinClause(String type, Alias<E> newAlias, IdAliasColumn<? super N> existingIdColumn, ForeignKeyAliasColumn<E, N> newForeignKeyColumn) {
    this(type, newAlias.getTableName(), newAlias.getName(), existingIdColumn.getQualifiedName(), newForeignKeyColumn.getQualifiedName());
  }

  public JoinClause(String type, Alias<E> newAlias, ForeignKeyAliasColumn<N, E> existingForeignKeyColumn, IdAliasColumn<? super E> newIdColum) {
    this(type, newAlias.getTableName(), newAlias.getName(), existingForeignKeyColumn.getQualifiedName(), newIdColum.getQualifiedName());
  }

  /** Wild cards for utility code. */
  public JoinClause(String type, Alias<?> newAlias, IdAliasColumn<?> existingAliasIdColum, IdAliasColumn<?> newAliasIdColumn) {
    this(type, newAlias.getTableName(), newAlias.getName(), existingAliasIdColum.getQualifiedName(), newAliasIdColumn.getQualifiedName());
  }

  private JoinClause(String type, String tableName, String aliasName, String existingColumn, String newColumn) {
    this.text = Join.space(type, Wrap.quotes(tableName), aliasName, "ON", existingColumn, "=", newColumn);
  }

  public String toString() {
    return this.text;
  }

}
