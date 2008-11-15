package org.exigencecorp.domainobjects.queries.columns;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Where;

public class ShortAliasColumn<T extends DomainObject> extends AliasColumn<T, Short, Integer> {

    public ShortAliasColumn(Alias<T> alias, String name, Shim<T, Short> shim) {
        super(alias, name, shim);
    }

    public Short toDomainValue(Integer jdbcValue) {
        return (jdbcValue == null) ? null : jdbcValue.shortValue();
    }

    public Integer toJdbcValue(Short domainValue) {
        return (domainValue == null) ? null : domainValue.intValue();
    }

    public Where moreThan(Short value) {
        return new Where(this.getQualifiedName() + " > ?", value);
    }

    public Where lessThan(Short value) {
        return new Where(this.getQualifiedName() + " < ?", value);
    }

}
