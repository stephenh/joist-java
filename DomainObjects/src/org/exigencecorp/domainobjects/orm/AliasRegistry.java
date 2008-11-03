package org.exigencecorp.domainobjects.orm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.queries.Alias;

public class AliasRegistry {

    private static final Map<Class<?>, Alias<?>> aliases = new ConcurrentHashMap<Class<?>, Alias<?>>();

    public static <T extends DomainObject> void register(Class<T> domainClass, Alias<T> alias) {
        AliasRegistry.aliases.put(domainClass, alias);
    }

    public static <T extends DomainObject> Alias<T> get(Class<T> domainClass) {
        return (Alias<T>) AliasRegistry.aliases.get(domainClass);
    }

}
