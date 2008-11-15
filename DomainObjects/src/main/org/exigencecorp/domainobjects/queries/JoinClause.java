package org.exigencecorp.domainobjects.queries;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.columns.ForeignKeyAliasColumn;
import org.exigencecorp.domainobjects.queries.columns.IdAliasColumn;
import org.exigencecorp.util.Join;

public class JoinClause {

    private final String text;

    /**
     * @param newAlias the alias we are introducing to the query and binding to the column <code>on</code>
     * @param on a foreign key column that points at <code>newAlias</code>'s table
     */
    public <T extends DomainObject, W extends DomainObject> JoinClause(String type, Alias<W> newAlias, ForeignKeyAliasColumn<T, W> on) {
        this.text = Join.space(type,//
            newAlias.getTableName(),
            newAlias.getName(),
            "ON",
            on.getQualifiedName(),
            "=",
            newAlias.getIdColumn().getQualifiedName());
    }

    /**
     * @param newAlias the alias we are introducing to the query and binding to the column <code>on</code>
     * @param on a regular int column (probably a base or sub class)
     */
    public JoinClause(String type, Alias<?> newAlias, IdAliasColumn<?> newAliasColumn, IdAliasColumn<?> on) {
        this.text = Join.space(type,//
            newAlias.getTableName(),
            newAlias.getName(),
            "ON",
            on.getQualifiedName(),
            "=",
            newAliasColumn.getQualifiedName());
    }

    public String toString() {
        return this.text;
    }

}
