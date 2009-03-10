package joist.domain.queries.columns;


import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.queries.Alias;
import joist.domain.queries.Order;
import joist.domain.queries.SelectItem;
import joist.domain.queries.Where;

/**
 * @param T the domain object type
 * @param U the domain property type
 * @param V the jdbc property type
 */
public abstract class AliasColumn<T extends DomainObject, U, V> {

    private final Alias<T> alias;
    private final String name;
    private final Shim<T, U> shim;

    protected AliasColumn(Alias<T> alias, String name, Shim<T, U> shim) {
        this.alias = alias;
        this.name = name;
        this.shim = shim;
    }

    public U getDomainValue(T instance) {
        return this.shim.get(instance);
    }

    public V getJdbcValue(T instance) {
        return this.toJdbcValue(this.getDomainValue(instance));
    }

    public void setJdbcValue(T instance, V jdbcValue) {
        this.shim.set(instance, this.toDomainValue(jdbcValue));
    }

    public U toDomainValue(V jdbcValue) {
        return (U) jdbcValue;
    }

    public V toJdbcValue(U domainValue) {
        return (V) domainValue;
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
