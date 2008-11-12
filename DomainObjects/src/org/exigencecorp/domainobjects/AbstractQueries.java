package org.exigencecorp.domainobjects;

import org.exigencecorp.domainobjects.orm.AliasRegistry;
import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.queries.Select;

public abstract class AbstractQueries<T extends DomainObject> {

    private final Class<T> domainType;

    protected AbstractQueries(Class<T> type) {
        this.domainType = type;
    }

    public T find(Integer id) {
        Alias<T> alias = this.getAlias();
        return Select.from(alias).where(alias.getIdColumn().equals(id)).unique();
    }

    private Alias<T> getAlias() {
        return AliasRegistry.get(this.domainType);
    }

}
