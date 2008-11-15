package org.exigencecorp.domainobjects.queries.columns;

import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Where;
import org.exigencecorp.util.Join;

/**
 * @param T should always be the root class--I think so
 */
public class IdAliasColumn<T extends DomainObject> extends AliasColumn<T, Integer, Integer> {

    public IdAliasColumn(final Alias<T> alias, String name, Shim<T, Integer> shim) {
        super(alias, name, shim);
    }

    public Where equals(Integer value) {
        return new Where(this.getQualifiedName() + " = ?", value);
    }

    public Where equals(T value) {
        return new Where(this.getQualifiedName() + " = ?", value.getId());
    }

    public Where moreThan(Integer value) {
        return new Where(this.getQualifiedName() + " > ?", value);
    }

    public Where lessThan(Integer value) {
        return new Where(this.getQualifiedName() + " < ?", value);
    }

    public Where in(List<Integer> ids) {
        return new Where(this.getQualifiedName() + " in (" + Join.comma(ids) + ")");
    }

}
