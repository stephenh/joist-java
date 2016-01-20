package joist.domain.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.exceptions.DisconnectedException;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.uow.UoW;
import joist.domain.util.ListProxy;
import joist.util.Copy;
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
  private final ListProxy.Delegate<U> listDelegate;
  private List<U> loaded;
  private List<U> proxy;

  public ForeignKeyListHolder(
    T parent,
    Alias<U> childAlias,
    ForeignKeyAliasColumn<U, T> childForeignKeyToParentColumn,
    ListProxy.Delegate<U> listDelegate) {
    this.parent = parent;
    this.childAlias = childAlias;
    this.childForeignKeyToParentColumn = childForeignKeyToParentColumn;
    this.listDelegate = listDelegate;
  }

  public List<U> get() {
    if (this.loaded == null) {
      if (this.parent.isNew() || UnitTesting.isEnabled()) {
        // parent is brand new, so don't bother hitting the database
        this.loaded = new ArrayList<U>();
      } else {
        if (!UoW.isOpen()) {
          throw new DisconnectedException();
        }
        if (!EagerLoading.isEnabled()) {
          // fetch only the children for this parent from the db
          Select<U> q = Select.from(this.childAlias);
          q.where(this.childForeignKeyToParentColumn.eq(this.parent));
          q.orderBy(this.childAlias.getIdColumn().asc());
          q.limit(UoW.getIdentityMap().getCurrentSizeLimit());
          this.loaded = q.list();
        } else {
          // preemptively fetch all children for all parents from the db
          MapToList<Long, U> byParentId = UoW.getEagerCache().get(this.childForeignKeyToParentColumn);
          if (!byParentId.containsKey(this.parent.getId())) {
            Collection<Long> idsToLoad = UoW.getEagerCache().getIdsToLoad(this.parent, this.childForeignKeyToParentColumn);
            if (!idsToLoad.contains(this.parent.getId())) {
              throw new IllegalStateException("Instance has been disconnected from the UoW: " + this.parent);
            }
            this.eagerlyLoad(byParentId, idsToLoad);
          }
          this.loaded = new ArrayList<U>();
          this.loaded.addAll(byParentId.get(this.parent.getId()));
        }
      }
      if (this.addedBeforeLoaded.size() > 0 || this.removedBeforeLoaded.size() > 0) {
        // apply back any adds/removes that we'd been holding off on
        this.loaded.addAll(this.addedBeforeLoaded);
        this.loaded.removeAll(this.removedBeforeLoaded);
        this.loaded = Copy.unique(this.loaded);
      }
      this.proxy = new ListProxy<U>(this.loaded, this.listDelegate);
    }
    return this.proxy;
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

  public void set(List<U> instances) {
    // We assume codegen classes are only calling this after they've done
    // a get(), so it's fine to clear/reset loaded to match the order
    // they are expecting. (Which, yes, will not be respected on the next
    // reload, but that is at least somewhat of a known surprise.)
    this.loaded.clear();
    this.loaded.addAll(instances);
  }

  private void eagerlyLoad(MapToList<Long, U> byParentId, Collection<Long> idsToLoad) {
    Select<U> q = Select.from(this.childAlias);
    q.where(this.childForeignKeyToParentColumn.in(idsToLoad));
    q.orderBy(this.childAlias.getIdColumn().asc());
    q.limit(UoW.getIdentityMap().getCurrentSizeLimit());
    for (U child : q.list()) {
      Long parentId = this.childForeignKeyToParentColumn.getDomainValue(child);
      byParentId.add(parentId, child);
    }
    // even if a parent didn't have any children, ensure it has an zero-long entry in the cache
    // so that we know not to re-query for its children when/if it's accessed
    for (Long currentlyLoadedParentId : idsToLoad) {
      byParentId.get(currentlyLoadedParentId);
    }
  }

  @Override
  public String toString() {
    return this.loaded != null ? this.loaded.toString() : "unloaded + " + this.addedBeforeLoaded + " - " + this.removedBeforeLoaded;
  }

}
