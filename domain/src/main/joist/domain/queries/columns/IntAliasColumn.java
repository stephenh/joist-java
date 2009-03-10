package joist.domain.queries.columns;

import java.util.List;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.queries.Alias;
import joist.domain.queries.Where;


import org.exigencecorp.util.Join;

public class IntAliasColumn<T extends DomainObject> extends AliasColumn<T, Integer, Integer> {

    public IntAliasColumn(Alias<T> alias, String name, Shim<T, Integer> shim) {
        super(alias, name, shim);
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
