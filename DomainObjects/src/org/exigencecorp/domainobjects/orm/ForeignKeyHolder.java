package org.exigencecorp.domainobjects.orm;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.uow.UoW;

/** A value holder that will lazy load the foreign key. */
public class ForeignKeyHolder<T extends DomainObject> {

    private Class<T> domainClass;
    private Integer id;
    private T instance;

    public ForeignKeyHolder(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    public T get() {
        if (this.instance == null && this.id != null && UoW.isOpen()) {
            this.instance = UoW.getCurrent().getRepository().load(this.domainClass, this.id);
        }
        return this.instance;
    }

    public void set(T instance) {
        this.instance = instance;
        if (instance == null) {
            this.id = null;
        }
    }

    public Integer getId() {
        if (this.instance != null) {
            return this.instance.getId().intValue();
        }
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
