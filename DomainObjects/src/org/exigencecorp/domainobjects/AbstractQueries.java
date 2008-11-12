package org.exigencecorp.domainobjects;

import org.exigencecorp.domainobjects.uow.UoW;

public abstract class AbstractQueries<T extends DomainObject> {

    private final Class<T> domainType;

    protected AbstractQueries(Class<T> type) {
        this.domainType = type;
    }

    public T find(Integer id) {
        // Use load as it hits the IdentityMap and could avoid an unneeded query
        return UoW.getCurrent().getRepository().load(this.domainType, id);
    }

}
