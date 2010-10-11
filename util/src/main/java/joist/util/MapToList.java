package joist.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MapToList<K, V> extends LinkedHashMap<K, List<V>> {

  private static final long serialVersionUID = -1;

  @Override
  public List<V> get(Object key) {
    List<V> values = super.get(key);
    if (values == null) {
      values = (List<V>) new ArrayList<Object>();
      this.put((K) key, values);
    }
    return values;
  }

  public void add(Object key) {
    this.get(key);
  }

  public void add(Object key, V value) {
    this.get(key).add(value);
  }

  public void remove(Object key, V value) {
    this.get(key).remove(value);
  }

}
