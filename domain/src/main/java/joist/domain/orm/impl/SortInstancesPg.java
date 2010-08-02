package joist.domain.orm.impl;

import java.util.Set;

import joist.domain.DomainObject;
import joist.util.MapToList;

/** Sorts instances by insert/update and by class. */
public class SortInstancesPg {

  public final MapToList<Class<DomainObject>, DomainObject> inserts = new MapToList<Class<DomainObject>, DomainObject>();
  public final MapToList<Class<DomainObject>, DomainObject> updates = new MapToList<Class<DomainObject>, DomainObject>();

  public SortInstancesPg(Set<DomainObject> instances) {
    for (DomainObject instance : instances) {
      if (instance.isNew()) {
        this.inserts.add(instance.getClass(), instance);
      } else {
        this.updates.add(instance.getClass(), instance);
      }
    }
  }

}
