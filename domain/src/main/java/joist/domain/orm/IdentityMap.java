package joist.domain.orm;

import java.util.HashMap;
import java.util.Map;

import joist.domain.DomainObject;
import joist.util.Log;

public class IdentityMap {

  // TODO: Use a weak value so that, once the user does not reference to
  // a domain object any more (and it's been flushed from the validate queue),
  // the GC can delete it?
  private final Map<String, DomainObject> objects = new HashMap<String, DomainObject>();

  public void store(DomainObject o) {
    Class<?> rootType = AliasRegistry.getRootClass(o.getClass());
    Integer id = o.getId().intValue();
    Log.trace("Storing {}#{} in identity map", rootType, id);
    if (this.objects.put(rootType + "#" + id, o) != null) {
      throw new RuntimeException("Domain object conflicts with an existing id " + o);
    }
  }

  public Object findOrNull(Class<?> type, Long id) {
    Class<?> rootType = AliasRegistry.getRootClass(type);
    Object o = this.objects.get(rootType + "#" + id);
    if (o != null) {
      Log.trace("Found {}#{} in identity map", rootType, id);
      return o;
    }
    Log.trace("Missed {}#{} in identity map", rootType, id);
    return null;
  }

  public int size() {
    return this.objects.size();
  }

}
