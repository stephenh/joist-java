package org.exigencecorp.domainobjects.queries.columns;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.orm.converters.Converter;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Order;
import org.exigencecorp.domainobjects.queries.SelectItem;
import org.exigencecorp.domainobjects.queries.SetItem;
import org.exigencecorp.domainobjects.queries.Where;

/**
 * @param T the domain object type
 * @param U the domain property type
 * @param V the jdbc property type
 */
public abstract class AliasColumn<T extends DomainObject, U, V> {

    private final Alias<T> alias;
    private final String name;
    private final Shim<T, U> shim;
    protected Converter<U, V> converter;

    protected AliasColumn(Alias<T> alias, String name, Shim<T, U> shim) {
        this.alias = alias;
        this.name = name;
        this.shim = shim;
        this.converter = null;
    }

    public void setJdbcValue(T instance, V jdbcValue) {
        // this.converter.toDomain(jdbcValue);
        U domainValue = (this.converter != null) ? this.converter.toDomain(jdbcValue) : (U) jdbcValue;
        this.shim.set(instance, domainValue);
    }

    public V getJdbcValue(T instance) {
        Object value = this.shim.get(instance);
        if (value instanceof AbstractDomainObject) {
            value = ((AbstractDomainObject) value).getId();
        }
        return (V) value;
    }

    // public void toSetItem(T instance, U value) {
    //    this.shim.set(instance, value);
    //}

    public SetItem toSetItem(T instance) {
        return this.toSetItem(this.getDomainValue(instance));
    }

    public SetItem toSetItem(U domainValue) {
        V jdbcValue = (this.converter != null) ? this.converter.toJdbc(domainValue) : (V) domainValue;
        return new SetItem(this, jdbcValue);
    }

    public U getDomainValue(T instance) {
        return this.shim.get(instance);
    }

    public Order asc() {
        return new Order(this.getQualifiedName());
    }

    public Order desc() {
        return new Order(this.getQualifiedName() + " desc");
    }

    public Where equals(U value) {
        return new Where(this.getQualifiedName() + " = ?", value);
    }

    public SelectItem as(String as) {
        return new SelectItem(this, as);
    }

    public String getQualifiedName() {
        return this.alias.getName() + "." + this.name;
    }

    public String getName() {
        return this.name;
    }

    public boolean skipUpdate() {
        return this.getName().equals("id") || this.getName().equals("version");
    }

}
