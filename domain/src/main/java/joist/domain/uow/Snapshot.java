package joist.domain.uow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joist.domain.AbstractDomainObject;
import joist.domain.DomainObject;
import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.columns.AliasColumn;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.util.MapToList;
import joist.util.Reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A snapshot of the data in a {@link UnitOfWork} to seed future {@link UnitOfWork}s without hitting the database.
 * 
 * The class and the data in it is immutable so can safely be shared across threads if needed.
 */
public class Snapshot {

  private static final Logger log = LoggerFactory.getLogger(UoW.class);
  // will be read-only after our initial construction
  private final List<InstanceData<?>> data = new ArrayList<InstanceData<?>>();
  // also read-only after construction
  private final Map<ForeignKeyAliasColumn<?, ?>, MapToList<Long, Long>> eagerCache = new HashMap<ForeignKeyAliasColumn<?, ?>, MapToList<Long, Long>>();

  Snapshot(UnitOfWork uow) {
    // copy the identity map
    for (Map.Entry<Class<?>, Map<Long, DomainObject>> e : uow.getIdentityMap().getObjects().entrySet()) {
      for (DomainObject instance : e.getValue().values()) {
        this.data.add(toJdbcValues(instance));
      }
    }
    // copy the eager cache (we assume it is on; it is hard-coded right now) to cache collections
    for (Map.Entry<ForeignKeyAliasColumn<?, ?>, MapToList<Long, DomainObject>> e : uow.getEagerCache().cache.entrySet()) {
      MapToList<Long, Long> ids = new MapToList<Long, Long>();
      for (Map.Entry<Long, List<DomainObject>> ee : e.getValue().entrySet()) {
        for (DomainObject instance : ee.getValue()) {
          ids.add(ee.getKey(), instance.getId());
        }
      }
      this.eagerCache.put(e.getKey(), ids);
    }
  }

  /** Populates a new {@link UnitOfWork} with our cached data. Could happen on a different thread. */
  public void populate(UnitOfWork uow) {
    for (InstanceData<?> instanceData : this.data) {
      uow.getIdentityMap().store(fromJdbcValues(instanceData));
    }
    for (Map.Entry<ForeignKeyAliasColumn<?, ?>, MapToList<Long, Long>> e : this.eagerCache.entrySet()) {
      ForeignKeyAliasColumn<DomainObject, ?> ac = (ForeignKeyAliasColumn<DomainObject, ?>) e.getKey();
      for (Map.Entry<Long, List<Long>> ee : e.getValue().entrySet()) {
        Long parentId = ee.getKey();
        for (Long childId : ee.getValue()) {
          DomainObject newInstance = (DomainObject) uow.getIdentityMap().findOrNull(ac.getAlias().getDomainClass(), childId);
          log.trace("Adding to eager cache {}", newInstance);
          uow.getEagerCache().get(ac).get(parentId).add(newInstance);
        }
      }
    }
  }

  private static <T extends DomainObject> T fromJdbcValues(InstanceData<T> instanceData) {
    T instance = Reflection.newInstance(instanceData.type);
    Alias<T> a = AliasRegistry.get(instanceData.type);
    int i = 0;
    for (AliasColumn<T, ?, ?> c : a.getColumns()) {
      setJdbcValue(c, instance, instanceData.jdbcValues[i++]);
    }
    AbstractDomainObject.setFromSnapshot(instance);
    return instance;
  }

  private static <T extends DomainObject> InstanceData<T> toJdbcValues(T instance) {
    Alias<T> a = AliasRegistry.get(instance); // could be a different subclass, so can't do this outside the loop
    Object[] state = new Object[a.getColumns().size()];
    int i = 0;
    for (AliasColumn<T, ?, ?> c : a.getColumns()) {
      state[i++] = c.getJdbcValue(instance);
    }
    return new InstanceData<T>(a.getDomainClass(), state);
  }

  private static <T extends DomainObject, V> void setJdbcValue(AliasColumn<T, ?, V> c, T instance, Object value) {
    c.setJdbcValue(instance, (V) value);
  }

  private static class InstanceData<T extends DomainObject> {
    private final Class<T> type;
    private final Object[] jdbcValues;

    private InstanceData(Class<T> type, Object[] jdbcValues) {
      this.type = type;
      this.jdbcValues = jdbcValues;
    }
  }

}
