package org.exigencecorp.domainobjects.queries.columns;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.SetItem;

public class BooleanAliasColumn<T extends DomainObject> extends AliasColumn<T, Boolean, Boolean> {

    public BooleanAliasColumn(Alias<T> alias, String name, Shim<T, Boolean> shim) {
        super(alias, name, shim);
    }

    public SetItem to(Boolean value) {
        return new SetItem(this, value);
    }
}
