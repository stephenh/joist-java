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
public class SortedInstances {

    public final MapToList<Class<DomainObject>, DomainObject> insertNewIds = new MapToList<Class<DomainObject>, DomainObject>();
    public final MapToList<Class<DomainObject>, DomainObject> insertHasIds = new MapToList<Class<DomainObject>, DomainObject>();
    public final MapToList<Class<DomainObject>, DomainObject> updates = new MapToList<Class<DomainObject>, DomainObject>();
    public final List<Class<DomainObject>> insertsByForeignKey = new ArrayList<Class<DomainObject>>();

    public SortedInstances(Set<DomainObject> instances) {
        for (DomainObject instance : instances) {
            if (instance.isNew()) {
                if (instance.getId() == null) {
                    this.insertNewIds.add(instance.getClass(), instance);
                } else {
                    this.insertHasIds.add(instance.getClass(), instance);
                }
            } else {
                this.updates.add(instance.getClass(), instance);
            }
        }
        this.insertsByForeignKey.addAll(this.insertNewIds.keySet());
        this.insertsByForeignKey.addAll(this.insertHasIds.keySet());
        Collections.sort(this.insertsByForeignKey, new Comparator<Class<DomainObject>>() {
            public int compare(Class<DomainObject> o1, Class<DomainObject> o2) {
                return AliasRegistry.get(o1).getOrder() - AliasRegistry.get(o2).getOrder();
            }
        });
    }

}
