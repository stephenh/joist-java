package joist.domain.orm.impl;

import java.util.Set;

import joist.domain.DomainObject;
import joist.util.MapToList;

/** Sorts instances by insert/update and by class. */
public class SortedInstances<T extends DomainObject> {

    public final MapToList<Class<T>, T> inserts = new MapToList<Class<T>, T>();
    public final MapToList<Class<T>, T> updates = new MapToList<Class<T>, T>();

    public SortedInstances(Set<T> instances) {
        for (T instance : instances) {
            if (instance.isNew()) {
                this.inserts.add(instance.getClass(), instance);
            } else {
                this.updates.add(instance.getClass(), instance);
            }
        }
    }
}
