package org.exigencecorp.domainobjects;

public class Id<T extends DomainObject> {

    private final Class<T> domainClass;
    private final Integer id;

    public Id(Class<T> domainClass, Integer id) {
        this.domainClass = domainClass;
        this.id = id;
    }

    public int intValue() {
        return this.id.intValue();
    }

    public Class<T> getDomainClass() {
        return this.domainClass;
    }

    public String toString() {
        return Integer.toString(this.id);
    }
}
