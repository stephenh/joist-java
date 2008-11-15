package org.exigencecorp.domainobjects.queries.columns;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Where;

public class LongAliasColumn<T extends DomainObject> extends AliasColumn<T, Long, Long> {

    public LongAliasColumn(Alias<T> alias, String name, Shim<T, Long> shim) {
        super(alias, name, shim);
    }

    public Where moreThan(Long value) {
        return new Where(this.getQualifiedName() + " > ?", value);
    }

    public Where lessThan(Long value) {
        return new Where(this.getQualifiedName() + " < ?", value);
    }

}
