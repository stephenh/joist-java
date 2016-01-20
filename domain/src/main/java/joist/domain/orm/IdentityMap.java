package joist.domain.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joist.domain.DomainObject;
import joist.util.MapToMap;

public class IdentityMap {

  private static final Logger log = LoggerFactory.getLogger(IdentityMap.class);
  private static final AtomicInteger defaultSizeLimit = new AtomicInteger(10000);
  private int sizeLimit = defaultSizeLimit.get();

  public static int getDefaultSizeLimit() {
    return defaultSizeLimit.get();
  }

  public static void setDefaultSizeLimit(int sizeLimit) {
    IdentityMap.defaultSizeLimit.set(sizeLimit);
  }

  /** Use {@link IdentityMap#getDefaultSizeLimit()} instead. */
  @Deprecated
  public static int getSizeLimit() {
    return defaultSizeLimit.get();
  }

  /** Use {@link IdentityMap#setDefaultSizeLimit(int)} instead. */
  @Deprecated
  public static void setSizeLimit(int sizeLimit) {
    IdentityMap.defaultSizeLimit.set(sizeLimit);
  }

  private final MapToMap<Class<?>, Long, DomainObject> objects = new MapToMap<Class<?>, Long, DomainObject>();
  private int size;

  public void store(DomainObject o) {
    Class<?> rootType = AliasRegistry.getRootClass(o.getClass());
    log.trace("Storing {}#{} in identity map", rootType, o.getId());
    if (this.objects.put(rootType, o.getId(), o) != null) {
      throw new RuntimeException("Domain object conflicts with an existing id " + o);
    }
    if (++this.size >= this.sizeLimit) {
      throw new IllegalStateException("IdentityMap grew over the " + this.sizeLimit + " instance limit");
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

  // named getCurrentSizeLimit because getSizeLimit already exists
  public int getCurrentSizeLimit() {
    return this.sizeLimit;
  }

  public void setCurrentSizeLimit(int sizeLimit) {
    this.sizeLimit = sizeLimit;
  }

}
