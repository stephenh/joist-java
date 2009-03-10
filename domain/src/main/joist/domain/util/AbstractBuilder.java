package joist.domain.util;


import joist.domain.DomainObject;
import joist.domain.uow.UoW;

public abstract class AbstractBuilder<T extends DomainObject> {

    // If in integration database mode
    private Class<T> instanceClass;
    private Integer instanceId;
    // If in unit ram mode
    private T instance;

    protected AbstractBuilder(T instance) {
        if (UoW.isOpen()) {
            if (instance.getId() == null) {
                UoW.flush();
            }
            this.instanceClass = (Class<T>) instance.getClass();
            this.instanceId = instance.getId();
        } else {
            this.instance = instance;
        }
    }

    public T get() {
        if (this.instanceId != null) {
            return UoW.load(this.instanceClass, this.instanceId);
        } else {
            return this.instance;
        }
    }
}
