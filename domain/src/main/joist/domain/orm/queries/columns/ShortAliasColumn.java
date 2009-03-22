package joist.domain.orm.queries.columns;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Where;

public class ShortAliasColumn<T extends DomainObject> extends AliasColumn<T, Short, Short> {

    public ShortAliasColumn(Alias<T> alias, String name, Shim<T, Short> shim) {
        super(alias, name, shim);
    }

    public Short toDomainValue(Short jdbcValue) {
        return (jdbcValue == null) ? null : jdbcValue.shortValue();
    }

    public Short toJdbcValue(Short domainValue) {
        return (domainValue == null) ? null : domainValue;
    }

    public Where moreThan(Short value) {
        return new Where(this.getQualifiedName() + " > ?", value);
    }

    public Where lessThan(Short value) {
        return new Where(this.getQualifiedName() + " < ?", value);
    }

}
