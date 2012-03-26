package joist.domain.orm.impl;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Delete;

/** A class that caches the SQL for how to delete a given table, needs to be thread-safe. */
public class InstanceDeleter<T extends DomainObject> {

  public static <T extends DomainObject> InstanceDeleter<T> get(Class<T> clazz) {
    // not worth caching right now
    return new InstanceDeleter<T>(AliasRegistry.get(clazz));
  }

  private final Alias<T> alias;

  public InstanceDeleter(Alias<T> alias) {
    this.alias = alias;
  }

  public void delete(List<T> instances) {
    List<Long> ids = this.getIds(instances);
    for (Alias<? super T> current = this.alias; current != null; current = current.getBaseClassAlias()) {
      if (current.isRootClass()) {
        Delete.from(current).where(current.getIdColumn().in(ids)).execute();
      } else {
        Delete.from(current).where(current.getSubClassIdColumn().in(ids)).execute();
      }
    }
  }

  private List<Long> getIds(List<T> instances) {
    List<Long> ids = new ArrayList<Long>();
    for (T instance : instances) {
      ids.add(instance.getId());
    }
    return ids;
  }

}
