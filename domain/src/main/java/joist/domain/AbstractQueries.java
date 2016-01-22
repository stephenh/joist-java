package joist.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import joist.domain.orm.AliasRegistry;
import joist.domain.orm.queries.Alias;
import joist.domain.orm.queries.Select;
import joist.domain.uow.UoW;

public abstract class AbstractQueries<T extends DomainObject> {

  private final Class<T> domainType;
  private final Alias<T> aliasType;

  protected AbstractQueries(Class<T> type) {
    this.domainType = type;
    this.aliasType = AliasRegistry.get(type);
    if (this.aliasType == null) {
      throw new IllegalStateException(type + " was not available in the AliasRegistry");
    }
  }

  public T find(Integer id) {
    return this.find(id == null ? ((Long) null) : new Long(id.longValue()));
  }

  public T find(Long id) {
    // Use load as it hits the IdentityMap and could avoid an unneeded query
    return UoW.load(this.domainType, id);
  }

  public List<T> find(Long... ids) {
    // Use load as it hits the IdentityMap and could avoid an unneeded query
    return UoW.load(this.domainType, Arrays.asList(ids));
  }

  public List<T> find(Collection<Long> ids) {
    // Use load as it hits the IdentityMap and could avoid an unneeded query
    return UoW.load(this.domainType, ids);
  }

  public long count() {
    return Select.from(this.aliasType).count();
  }

  public List<Long> findAllIds() {
    return Select.from(this.aliasType).listIds();
  }

  public void delete(T instance) {
    instance.clearAssociations();
    if (UoW.isOpen()) {
      UoW.delete(instance);
    }
  }

}
