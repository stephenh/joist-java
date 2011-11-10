package joist.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

public class Copy {

  private Copy() {
  }

  // List methods

  public static <T> FluentList<T> list(T value) {
    FluentList<T> list = new FluentList<T>();
    list.add(value);
    return list;
  }

  public static <T> FluentList<T> list(T... array) {
    FluentList<T> list = new FluentList<T>();
    if (array != null) {
      for (T a : array) {
        list.add(a);
      }
    }
    return list;
  }

  /** @return a copy of {@code collection}, same thing as Copy.list but with a name more amenable to static imports. */
  public static <T> FluentList<T> copy(Collection<T> collection) {
    return Copy.list(collection);
  }

  public static <T> FluentList<T> list(Collection<T> collection) {
    return new FluentList<T>(collection);
  }

  public static <T> FluentList<T> reverse(Collection<T> source) {
    return new FluentList<T>(source).reverse();
  }

  public static <T> FluentList<T> unique(Collection<T> source) {
    return new FluentList<T>(source).unique();
  }

  public static <T> List<T> union(Collection<? extends T>... sources) {
    ArrayList<T> union = new ArrayList<T>();
    for (Collection<? extends T> source : sources) {
      union.addAll(source);
    }
    return union;
  }

  // Array methods

  public static <T> T[] array(Class<T> type, List<T> list) {
    T[] array = (T[]) Array.newInstance(type, list.size());
    return list.toArray(array);
  }

  // Map methods

  public static <K, V> FluentMap<K, V> map(K key, V value) {
    return new FluentMap<K, V>().with(key, value);
  }

  // MapToList methods

  public static <T> MapToList<T, T> map(MapToList<T, T> source) {
    MapToList<T, T> copy = new MapToList<T, T>();
    for (Entry<T, List<T>> e : source.entrySet()) {
      copy.put(e.getKey(), Copy.list(e.getValue()));
    }
    return copy;
  }

}
