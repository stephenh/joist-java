package joist.domain.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.uow.UoW;
import joist.util.Copy;
import joist.util.FluentList;
import joist.util.MapToList;

/**
 * Keeps a per-UoW cache of {@code parent -> [children]} mappings for eager loading.
 *
 * To avoid the N+1 problem, when loading the children for a parent, we can load
 * all of the children for all of the parents that are currently loaded in the UoW.
 * {@code EagerCache} then caches the map of {@code parent -> [children]} so that
 * when accessing other {@code parent.getChildren()} collections, we can use the
 * already-retrieved children and already-retrieved mapping instead of hitting
 * the database again.
 */
public class EagerCache {

  // TODO needs to private again...
  public final Map<ForeignKeyAliasColumn<?, ?>, MapToList<Long, DomainObject>> cache = new HashMap<ForeignKeyAliasColumn<?, ?>, MapToList<Long, DomainObject>>();
  private List<DomainObject> instancesToNotEagerlyLoad = new ArrayList<DomainObject>();

  @SuppressWarnings("unchecked")
  public <U extends DomainObject> MapToList<Long, U> get(ForeignKeyAliasColumn<U, ?> ac) {
    MapToList<Long, U> map = (MapToList<Long, U>) this.cache.get(ac);
    if (map == null) {
      // create and cache a new map if this is the first load for the parent
      map = new MapToList<Long, U>();
      this.cache.put(ac, (MapToList<Long, DomainObject>) map);
    }
    return map;
  }

  public <T extends DomainObject, U extends DomainObject> Collection<Long> getIdsToLoad(T parent, ForeignKeyAliasColumn<U, ?> ac) {
    MapToList<Long, U> byParentId = this.get(ac);
    // some (or potentially none yet) children were fetched, but our parent wasn't in the UoW at the time
    Collection<Long> alreadyFetchedIds = byParentId.keySet();
    FluentList<Long> allParentIds = Copy.list(UoW.getIdentityMap().getIdsOf(parent.getClass()));
    Collection<Long> idsToLoad = allParentIds.without(alreadyFetchedIds);
    for (DomainObject instance : this.instancesToNotEagerlyLoad) {
      if (instance != parent) {
        idsToLoad.remove(instance.getId());
      }
    }
    return idsToLoad;
  }

  public void doNotEagerlyLoadFor(DomainObject instance) {
    this.instancesToNotEagerlyLoad.add(instance);
  }
}
