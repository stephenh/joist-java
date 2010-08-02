package joist.domain;

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
  }

  public T find(Integer id) {
    // Use load as it hits the IdentityMap and could avoid an unneeded query
    return UoW.load(this.domainType, id);
  }

  public long count() {
    return Select.from(this.aliasType).count();
  }

  public List<Integer> findAllIds() {
    return Select.from(this.aliasType).listIds();
  }

  public void delete(T instance) {
    instance.clearAssociations();
    if (UoW.isOpen()) {
      UoW.delete(instance);
    }
  }

}
