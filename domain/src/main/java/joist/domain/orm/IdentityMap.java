package joist.domain.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import joist.domain.DomainObject;
import joist.util.MapToMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdentityMap {

  private static final AtomicInteger sizeLimit = new AtomicInteger(10000);

  public static int getSizeLimit() {
    return sizeLimit.get();
  }

  public static void setSizeLimit(int sizeLimit) {
    IdentityMap.sizeLimit.set(sizeLimit);
  }

  private final MapToMap<Class<?>, Long, DomainObject> objects = new MapToMap<Class<?>, Long, DomainObject>();
  private int size;

  public void store(DomainObject o) {
    Class<?> rootType = AliasRegistry.getRootClass(o.getClass());
    log.trace("Storing {}#{} in identity map", rootType, o.getId());
    if (this.objects.put(rootType, o.getId(), o) != null) {
      throw new RuntimeException("Domain object conflicts with an existing id " + o);
    }
    if (++this.size >= getSizeLimit()) {
      throw new IllegalStateException("IdentityMap grew over the " + getSizeLimit() + " instance limit");
    }
  }

  public Object findOrNull(Class<?> type, Long id) {
    Class<?> rootType = AliasRegistry.getRootClass(type);
    Object o = this.objects.get(rootType, id);
    if (o != null) {
      log.trace("Found {}#{} in identity map", rootType, id);
      return o;
    }
    log.trace("Missed {}#{} in identity map", rootType, id);
    return null;
  }

  public int size() {
    return this.size;
  }

  public <T> Collection<T> getInstancesOf(Class<T> type) {
    Collection<T> instances = new ArrayList<T>();
    Class<?> rootType = AliasRegistry.getRootClass(type);
    Map<Long, DomainObject> forRootType = this.objects.get(rootType);
    if (forRootType != null) {
      for (DomainObject object : forRootType.values()) {
        // we cache based on rootType, but still want to ensure all returned
        // typed are of the right subclass
        if (type.isInstance(object)) {
          instances.add(type.cast(object));
        }
      }
    }
    return instances;
  }

  public Collection<Long> getIdsOf(Class<?> type) {
    Class<?> rootType = AliasRegistry.getRootClass(type);
    return this.objects.getSubKeysOf(rootType);
  }

  // only for Snapshot for now
  public MapToMap<Class<?>, Long, DomainObject> getObjects() {
    return this.objects;
  }

}
