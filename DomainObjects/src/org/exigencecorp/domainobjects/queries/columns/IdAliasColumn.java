package org.exigencecorp.domainobjects.queries.columns;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Id;
import org.exigencecorp.domainobjects.Ids;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.converters.Converter;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Where;
import org.exigencecorp.util.Join;

/**
 * @param T should always be the root class--I think so
 */
public class IdAliasColumn<T extends DomainObject> extends AliasColumn<T, Id<T>, Integer> {

    public IdAliasColumn(final Alias<T> alias, String name, Shim<T, Id<T>> shim) {
        super(alias, name, shim);

        this.converter = new Converter<Id<T>, Integer>() {
            public Integer toJdbc(Id<T> value) {
                return value.intValue();
            }

            public Id<T> toDomain(Integer value) {
                return new Id<T>(alias.getDomainClass(), value);
            }
        };
    }

    public Where equals(Integer value) {
        return new Where(this.getQualifiedName() + " = ?", value);
    }

    public Where equals(Id<T> value) {
        return new Where(this.getQualifiedName() + " = ?", value.intValue());
    }

    public Where equalsRuntimeChecked(Id<?> value) {
        return new Where(this.getQualifiedName() + " = ?", value.intValue());
    }

    public Where moreThan(Integer value) {
        return new Where(this.getQualifiedName() + " > ?", value);
    }

    public Where lessThan(Integer value) {
        return new Where(this.getQualifiedName() + " < ?", value);
    }

    public Where in(Ids<T> ids) {
        return new Where(this.getQualifiedName() + " in (" + Join.comma(ids.getIds()) + ")");
    }

}
