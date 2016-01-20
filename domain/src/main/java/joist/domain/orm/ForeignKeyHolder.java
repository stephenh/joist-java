package joist.domain.orm;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.apache.commons.lang.ObjectUtils;

import joist.domain.DomainObject;
import joist.domain.exceptions.DisconnectedException;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Select;
import joist.domain.orm.queries.columns.ForeignKeyAliasColumn;
import joist.domain.uow.UoW;
import joist.util.Default;

/**
 * A value holder that will lazy load the foreign key.
 *
 * @param C the type of the many (child) class that has the ForeignKeyHolder
 * @param P the type of the one (parent) class that is pointed at
 */
public class ForeignKeyHolder<C extends DomainObject, P extends DomainObject> {

  private final Class<C> childClass;
  private final ForeignKeyAliasColumn<C, P> childColumn;
  private final Class<P> parentClass;
  private final Alias<P> parentAlias;
  private Long id;
  private P instance;

  public ForeignKeyHolder(Class<C> childClass, Class<P> domainClass, Alias<P> domainAlias, ForeignKeyAliasColumn<C, P> childColumn) {
    this.childClass = childClass;
    this.childColumn = childColumn;
    this.parentClass = domainClass;
    this.parentAlias = domainAlias;
  }

  public P get() {
    if (this.instance == null && this.id != null) {
      if (!UoW.isOpen()) {
        throw new DisconnectedException();
      }
      if (!EagerLoading.isEnabled()) {
        // will make a query for just this id, only if needed
        this.instance = UoW.load(this.parentClass, this.id);
      } else {
        // see if the parent is loaded, but don't make a query while doing so (like UoW.load does)
        this.instance = (P) UoW.getIdentityMap().findOrNull(this.parentClass, this.id);
        if (this.instance == null) {
          // get the parent ids of all currently-loaded child classes
          Collection<Long> parentIds = new LinkedHashSet<Long>();
          for (C child : UoW.getIdentityMap().getInstancesOf(this.childClass)) {
            Long parentId = this.childColumn.getJdbcValue(child);
            if (parentId != null) {
              parentIds.add(parentId);
            }
          }
          // subtract any ids that are already loaded
          for (P parent : UoW.getIdentityMap().getInstancesOf(this.parentClass)) {
            parentIds.remove(parent.getId());
          }
          // if even our own id was not in the list, that is bad, we're from another UoW
          if (parentIds.size() == 0) {
            throw new IllegalStateException("Instance has been disconnected from the UoW");
          }
          Select<P> q = Select.from(this.parentAlias);
          q.where(this.parentAlias.getIdColumn().in(parentIds));
          q.limit(UoW.getIdentityMap().getCurrentSizeLimit());
          q.list(); // will populate the UoW IdentityMap with all fetched parents
          this.instance = (P) UoW.getIdentityMap().findOrNull(this.parentClass, this.id);
        }
      }
    }
    return this.instance;
  }

  public void set(P instance) {
    this.instance = instance;
    this.id = instance == null ? null : instance.getId();
  }

  public Long getId() {
    if (this.instance != null) {
      // Return -1 as the dummy value that we have a value, but do not
      // know its id yet. So far this is only for the NotNull rule so
      // it can still use the xxxId shim
      return Default.value(this.instance.getId(), -1l);
    }
    return this.id;
  }

  public void setId(Long id) {
    if (this.instance != null) {
      throw new IllegalStateException("An instance has already been associated");
    }
    this.id = id;
  }

  @Override
  public String toString() {
    return this.instance != null ? this.instance.toString() : this.childClass.getSimpleName() + "[" + ObjectUtils.toString(this.id, null) + "]";
  }

}
