package joist.domain.orm;

import java.util.HashMap;
import java.util.Map;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
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

  private final Map<ForeignKeyAliasColumn<?, ?>, MapToList<?, ?>> cache = new HashMap<ForeignKeyAliasColumn<?, ?>, MapToList<?, ?>>();

  public <U extends DomainObject> MapToList<Long, U> get(ForeignKeyAliasColumn<U, ?> ac) {
    return (MapToList<Long, U>) this.cache.get(ac);
  }

  public <U extends DomainObject> void put(ForeignKeyAliasColumn<U, ?> ac, MapToList<Long, U> byParentId) {
    this.cache.put(ac, byParentId);
  }

}
