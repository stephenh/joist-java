package org.exigencecorp.domainobjects.queries.columns;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

public class StringAliasColumn<T extends DomainObject> extends AliasColumn<T, String, String> {

    public StringAliasColumn(Alias<T> alias, String name, Shim<T, String> shim) {
        super(alias, name, shim);
    }

}
