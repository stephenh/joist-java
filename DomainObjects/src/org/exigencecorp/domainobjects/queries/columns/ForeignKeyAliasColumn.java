package org.exigencecorp.domainobjects.queries.columns;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Where;

/**
 * @param T the domain object the column is within
 * @param W the domain object the column points to
 */
public class ForeignKeyAliasColumn<T extends DomainObject, W extends DomainObject> extends AliasColumn<T, Integer, Integer> {

    public ForeignKeyAliasColumn(Alias<T> alias, String name, Shim<T, Integer> shim) {
        super(alias, name, shim);
    }

    public Where equals(Id<W> value) {
        return new Where(this.getQualifiedName() + " = ?", value.intValue());
    }

}
