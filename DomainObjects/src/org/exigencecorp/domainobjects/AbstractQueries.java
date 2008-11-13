package org.exigencecorp.domainobjects;

import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;
import org.exigencecorp.domainobjects.uow.UoW;

public abstract class AbstractQueries<T extends DomainObject> {

    private final Class<T> domainType;
    private final Alias<T> aliasType;

    protected AbstractQueries(Class<T> type) {
        this.domainType = type;
        this.aliasType = AliasRegistry.get(type);
    }

    public T find(Integer id) {
        // Use load as it hits the IdentityMap and could avoid an unneeded query
        return UoW.getCurrent().getRepository().load(this.domainType, id);
    }

    public long count() {
        return Select.from(this.aliasType).count();
    }

    public Ids<T> findAllIds() {
        return Select.from(this.aliasType).listIds();
    }

}
