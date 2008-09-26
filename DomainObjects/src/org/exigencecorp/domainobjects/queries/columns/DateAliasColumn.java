package org.exigencecorp.domainobjects.queries.columns;

import java.sql.Date;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;

public class DateAliasColumn<T extends DomainObject> extends AliasColumn<T, Date, Date> {

    public DateAliasColumn(Alias<T> alias, String name, Shim<T, Date> shim) {
        super(alias, name, shim);
    }

}
