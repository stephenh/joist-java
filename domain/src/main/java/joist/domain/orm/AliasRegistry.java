package joist.domain.orm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import joist.domain.DomainObject;
import joist.domain.orm.queries.Alias;
import joist.util.Reflection;

public class AliasRegistry {

  private static final Map<Class<?>, Alias<?>> aliases = new ConcurrentHashMap<Class<?>, Alias<?>>();
  private static final Map<Class<?>, Class<?>> rootClasses = new ConcurrentHashMap<Class<?>, Class<?>>();

  public static <T extends DomainObject> void register(Class<T> domainClass, Alias<T> alias) {
    AliasRegistry.aliases.put(domainClass, alias);
    AliasRegistry.rootClasses.put(domainClass, alias.getDomainRootClass());
  }

  public static <T extends DomainObject> Alias<T> get(Class<T> domainClass) {
    Alias<T> a = (Alias<T>) AliasRegistry.aliases.get(domainClass);
    Class<? super T> currentClass = domainClass;
    while (a == null) {
      // The static initializer for domainClass's codegen may not have been ran yet
      Reflection.forName(currentClass.getName());
      a = (Alias<T>) AliasRegistry.aliases.get(currentClass);
      // if a is still null, domainClass is probably a subclass of a domain
      // class, e.g. Parent p = new Parent() { ... }.
      if (a == null) {
        currentClass = currentClass.getSuperclass();
      }
    }
    return a;
  }

  public static <T extends DomainObject> Alias<T> get(T instance) {
    return (Alias<T>) AliasRegistry.aliases.get(instance.getClass());
  }

  public static Class<?> getRootClass(Class<?> domainClass) {
    return AliasRegistry.rootClasses.get(domainClass);
  }

}
