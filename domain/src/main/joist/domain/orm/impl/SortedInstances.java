package joist.domain.orm.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.util.MapToList;

/** Sorts instances by insert/update and by class. */
public class SortedInstances<T extends DomainObject> {

    public final MapToList<Class<T>, T> inserts = new MapToList<Class<T>, T>();
    public final MapToList<Class<T>, T> updates = new MapToList<Class<T>, T>();
    public final List<Class<T>> insertsByForeignKey = new ArrayList<Class<T>>();

    public SortedInstances(Set<T> instances) {
        for (T instance : instances) {
            if (instance.isNew()) {
                this.inserts.add(instance.getClass(), instance);
            } else {
                this.updates.add(instance.getClass(), instance);
            }
        }
        this.insertsByForeignKey.addAll(this.inserts.keySet());
        Collections.sort(this.insertsByForeignKey, new Comparator<Class<T>>() {
            public int compare(Class<T> o1, Class<T> o2) {
                return AliasRegistry.get(o1).getOrder() - AliasRegistry.get(o2).getOrder();
            }
        });
    }

}
