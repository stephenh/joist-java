package joist.domain.queries.columns;

import java.sql.Date;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.queries.Alias;



public class DateAliasColumn<T extends DomainObject> extends AliasColumn<T, Date, Date> {

    public DateAliasColumn(Alias<T> alias, String name, Shim<T, Date> shim) {
        super(alias, name, shim);
    }

}
