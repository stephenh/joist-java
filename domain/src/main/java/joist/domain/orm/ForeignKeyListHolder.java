package joist.domain.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.exceptions.DisconnectedException;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.uow.UoW;
import joist.util.Copy;
import joist.util.FluentList;
import joist.util.MapToList;

/**
 * A value holder that will lazy load the objects inferred by their foreign key to us.
 *
 * Until <code>loaded</code> is fetched from the database (if the unit of work is
 * open), we keep track of adds/removes and then only if truly asked for the entire
 * collection do we integrate the database results with the captured adds/removes.
 *
 * @param T the parent domain object
 * @param U the child domain object (the one with the foreign key in it)
 */
public class ForeignKeyListHolder<T extends DomainObject, U extends DomainObject> {

  private final T parent;
  private final Alias<U> childAlias;
  private final ForeignKeyAliasColumn<U, T> childForeignKeyToParentColumn;
  private final List<U> addedBeforeLoaded = new ArrayList<U>();
  private final List<U> removedBeforeLoaded = new ArrayList<U>();
  private List<U> loaded;
  private List<U> readOnly;

  public ForeignKeyListHolder(T parent, Alias<U> childAlias, ForeignKeyAliasColumn<U, T> childForeignKeyToParentColumn) {
    this.parent = parent;
    this.childAlias = childAlias;
    this.childForeignKeyToParentColumn = childForeignKeyToParentColumn;
  }

  public List<U> get() {
    if (this.loaded == null) {
      if (!this.parent.isNew()) {
        if (!UoW.isOpen()) {
          throw new DisconnectedException();
        }
        // hardcoded to true for now
        boolean shouldEagerLoad = true;
        if (!shouldEagerLoad) {
          // fetch only the children for this parent from the db
          Select<U> q = Select.from(this.childAlias);
          q.where(this.childForeignKeyToParentColumn.eq(this.parent));
          q.orderBy(this.childAlias.getIdColumn().asc());
          this.loaded = q.list();
        } else {
          // preemptively fetch all children for all parents from the db
          MapToList<Long, U> byParentId = UoW.getEagerCache().get(this.childForeignKeyToParentColumn);
          if (byParentId == null) {
            // no children have been fetched for any parents yet
            byParentId = this.eagerlyLoad(null, UoW.getIdentityMap().getIdsOf(this.parent.getClass()));
          } else if (!byParentId.containsKey(this.parent.getId())) {
            // a bunch of children were fetched, but our parent wasn't in the UoW at the time
            Collection<Long> alreadyFetchedIds = byParentId.keySet();
            FluentList<Long> allParentIds = Copy.list(UoW.getIdentityMap().getIdsOf(this.parent.getClass()));
            byParentId = this.eagerlyLoad(byParentId, allParentIds.without(alreadyFetchedIds));
          }
          this.loaded = new ArrayList<U>();
          this.loaded.addAll(byParentId.get(this.parent.getId()));
        }
      } else {
        // parent is brand new, so don't bother hitting the database
        this.loaded = new ArrayList<U>();
      }
      if (this.addedBeforeLoaded.size() > 0 || this.removedBeforeLoaded.size() > 0) {
        // apply back any adds/removes that we'd been holding off on
        this.loaded.addAll(this.addedBeforeLoaded);
        this.loaded.removeAll(this.removedBeforeLoaded);
        this.loaded = Copy.unique(this.loaded);
      }
      // make a read-only wrapper around the list to hand out to clients
      this.readOnly = Collections.unmodifiableList(this.loaded);
    }
    return this.readOnly;
  }

  public void add(U instance) {
    if (this.loaded == null) {
      this.removedBeforeLoaded.remove(instance);
      this.addedBeforeLoaded.add(instance);
    } else {
      this.loaded.add(instance);
    }
  }

  public void remove(U instance) {
    if (this.loaded == null) {
      this.addedBeforeLoaded.remove(instance);
      this.removedBeforeLoaded.add(instance);
    } else {
      this.loaded.remove(instance);
    }
  }

  private MapToList<Long, U> eagerlyLoad(MapToList<Long, U> byParentId, Collection<Long> idsToLoad) {
    if (byParentId == null) {
      // create and cache a new map if this is the first load for the parent
      byParentId = new MapToList<Long, U>();
      UoW.getEagerCache().put(this.childForeignKeyToParentColumn, byParentId);
    }
    Select<U> q = Select.from(this.childAlias);
    q.where(this.childForeignKeyToParentColumn.in(idsToLoad));
    q.orderBy(this.childAlias.getIdColumn().asc());
    for (U child : q.list()) {
      Long parentId = this.childForeignKeyToParentColumn.getDomainValue(child);
      byParentId.add(parentId, child);
    }
    // even if a parent didn't have any children, ensure it has an zero-long entry in the cache
    // so that we know not to re-query for its children when/if it's accessed
    for (Long currentlyLoadedParentId : idsToLoad) {
      byParentId.get(currentlyLoadedParentId);
    }
    return byParentId;
  }

  @Override
  public String toString() {
    return this.loaded != null ? this.loaded.toString() : this.addedBeforeLoaded + " - " + this.removedBeforeLoaded;
  }

}
