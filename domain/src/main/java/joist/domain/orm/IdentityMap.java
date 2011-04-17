package joist.domain.orm;

import java.util.Collection;

import joist.domain.DomainObject;
import joist.util.Log;
import joist.util.MapToMap;

public class IdentityMap {

  // TODO: Use a weak value so that, once the user does not reference to
  // a domain object any more (and it's been flushed from the validate queue),
  // the GC can delete it? Per Click, having the GC drive app behavior is a bad idea.
  private final MapToMap<Class<?>, Long, DomainObject> objects = new MapToMap<Class<?>, Long, DomainObject>();

  public void store(DomainObject o) {
    Class<?> rootType = AliasRegistry.getRootClass(o.getClass());
    Log.trace("Storing {}#{} in identity map", rootType, o.getId());
    if (this.objects.put(rootType, o.getId(), o) != null) {
      throw new RuntimeException("Domain object conflicts with an existing id " + o);
    }
  }

  public Object findOrNull(Class<?> type, Long id) {
    Class<?> rootType = AliasRegistry.getRootClass(type);
    Object o = this.objects.get(rootType, id);
    if (o != null) {
      Log.trace("Found {}#{} in identity map", rootType, id);
      return o;
    }
    Log.trace("Missed {}#{} in identity map", rootType, id);
    return null;
  }

  public int size() {
    return this.objects.totalSize();
  }

  public Collection<Long> getIdsOf(Class<?> type) {
    Class<?> rootType = AliasRegistry.getRootClass(type);
    return this.objects.getSubKeysOf(rootType);
  }

}
