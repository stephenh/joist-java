package joist.domain.orm.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.util.MapToList;

/** Sorts instances by insert/update and by class, then foreign key order. */
public class SortInstancesMySQL {

  public final MapToList<Class<DomainObject>, DomainObject> insertNewIds = new MapToList<Class<DomainObject>, DomainObject>();
  public final MapToList<Class<DomainObject>, DomainObject> insertHasIds = new MapToList<Class<DomainObject>, DomainObject>();
  public final MapToList<Class<DomainObject>, DomainObject> updates = new MapToList<Class<DomainObject>, DomainObject>();
  public final MapToList<Class<DomainObject>, DomainObject> deletes = new MapToList<Class<DomainObject>, DomainObject>();
  public final List<Class<DomainObject>> insertsByForeignKey = new ArrayList<Class<DomainObject>>();
  public final List<Class<DomainObject>> deletesByForeignKey = new ArrayList<Class<DomainObject>>();

  public SortInstancesMySQL(Set<DomainObject> insertOrUpdate, Set<DomainObject> delete) {
    for (DomainObject instance : insertOrUpdate) {
      if (instance.isNew()) {
        if (instance.getId() == null) {
          this.insertNewIds.add(instance.getClass(), instance);
        } else {
          this.insertHasIds.add(instance.getClass(), instance); // the user provided an id
        }
      } else {
        this.updates.add(instance.getClass(), instance);
      }
    }
    for (DomainObject instance : delete) {
      if (!instance.isNew()) {
        this.deletes.add(instance.getClass(), instance);
      }
    }
    this.insertsByForeignKey.addAll(this.insertNewIds.keySet());
    // insertsByForeignKey should really be a set, but then we can't sort it
    for (Class<DomainObject> clazz : this.insertHasIds.keySet()) {
      if (!this.insertsByForeignKey.contains(clazz)) {
        this.insertsByForeignKey.add(clazz);
      }
    }
    Collections.sort(this.insertsByForeignKey, new Comparator<Class<DomainObject>>() {
      public int compare(Class<DomainObject> o1, Class<DomainObject> o2) {
        return AliasRegistry.get(o1).getOrder() - AliasRegistry.get(o2).getOrder();
      }
    });
    // delete, sort by reverse order
    this.deletesByForeignKey.addAll(this.deletes.keySet());
    Collections.sort(this.deletesByForeignKey, new Comparator<Class<DomainObject>>() {
      public int compare(Class<DomainObject> o1, Class<DomainObject> o2) {
        return AliasRegistry.get(o2).getOrder() - AliasRegistry.get(o1).getOrder();
      }
    });
  }

}
