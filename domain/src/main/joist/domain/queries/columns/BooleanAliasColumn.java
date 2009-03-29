package joist.domain.queries.columns;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.queries.Alias;
import joist.domain.queries.SetItem;

public class BooleanAliasColumn<T extends DomainObject> extends AliasColumn<T, Boolean, Boolean> {

    public BooleanAliasColumn(Alias<T> alias, String name, Shim<T, Boolean> shim) {
        super(alias, name, shim);
    }

    public SetItem<T> to(Boolean value) {
        return new SetItem<T>(this, value);
    }
}
