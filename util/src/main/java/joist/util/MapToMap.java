package joist.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapToMap<K1, K2, V> extends LinkedHashMap<K1, Map<K2, V>> {

  private static final long serialVersionUID = 1L;

  public V put(K1 key1, K2 key2, V value) {
    Map<K2, V> m = super.get(key1);
    if (m == null) {
      m = new LinkedHashMap<K2, V>();
      super.put(key1, m);
    }
    return m.put(key2, value);
  }

  public V get(K1 key1, K2 key2) {
    Map<K2, V> m = super.get(key1);
    if (m == null) {
      return null;
    }
    return m.get(key2);
  }

  public Collection<K2> getSubKeysOf(K1 key1) {
    Map<K2, V> m = super.get(key1);
    if (m == null) {
      return Collections.emptySet();
    } else {
      return m.keySet();
    }
  }

  public int totalSize() {
    int i = 0;
    for (Map<K2, V> m : this.values()) {
      i += m.size();
    }
    return i;
  }

}
