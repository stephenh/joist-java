package joist.domain.queries.columns;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.queries.Alias;

public class ByteArrayAliasColumn<T extends DomainObject> extends AliasColumn<T, byte[], byte[]> {

    public ByteArrayAliasColumn(Alias<T> alias, String name, Shim<T, byte[]> shim) {
        super(alias, name, shim);
    }

}
