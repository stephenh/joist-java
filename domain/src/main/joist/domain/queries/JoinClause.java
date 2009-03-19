package joist.domain.queries;

import joist.domain.DomainObject;
import joist.domain.queries.columns.ForeignKeyAliasColumn;
import joist.domain.queries.columns.IdAliasColumn;
import joist.util.Join;
import joist.util.Wrap;

public class JoinClause {

    private final String text;

    public <T extends DomainObject, W extends DomainObject> JoinClause(String type, Alias<W> newAlias, ForeignKeyAliasColumn<T, W> on) {
        this(type, newAlias.getTableName(), newAlias.getName(), on.getQualifiedName(), newAlias.getIdColumn().getQualifiedName());
    }

    public JoinClause(String type, Alias<?> newAlias, IdAliasColumn<?> existingAliasIdColum, IdAliasColumn<?> newAliasIdColumn) {
        this(type, newAlias.getTableName(), newAlias.getName(), existingAliasIdColum.getQualifiedName(), newAliasIdColumn.getQualifiedName());
    }

    public <T extends DomainObject, W extends DomainObject> JoinClause(
        String type,
        Alias<T> newAlias,
        IdAliasColumn<? super W> existingIdColumn,
        ForeignKeyAliasColumn<T, W> newForeignKeyColumn) {
        this(type, newAlias.getTableName(), newAlias.getName(), existingIdColumn.getQualifiedName(), newForeignKeyColumn.getQualifiedName());
    }

    public <T extends DomainObject, W extends DomainObject> JoinClause(
        String type,
        Alias<T> newAlias,
        ForeignKeyAliasColumn<W, T> existingForeignKeyColumn,
        IdAliasColumn<? super T> newIdColum) {
        this(type, newAlias.getTableName(), newAlias.getName(), existingForeignKeyColumn.getQualifiedName(), newIdColum.getQualifiedName());
    }

    private JoinClause(String type, String tableName, String aliasName, String existingColumn, String newColumn) {
        this.text = Join.space(type, Wrap.quotes(tableName), aliasName, "ON", existingColumn, "=", newColumn);
    }

    public String toString() {
        return this.text;
    }

}
