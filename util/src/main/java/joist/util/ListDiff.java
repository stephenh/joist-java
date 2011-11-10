package joist.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListDiff<T> {

  public static <T> ListDiff<T> of(Collection<T> original, Collection<T> updated) {
    List<T> added = new ArrayList<T>();
    List<T> removed = new ArrayList<T>();
    for (T t : original) {
      if (!updated.contains(t)) {
        removed.add(t);
      }
    }
    for (T t : updated) {
      if (!original.contains(t)) {
        added.add(t);
      }
    }
    return new ListDiff<T>(added, removed);
  }

  public final List<T> added;
  public final List<T> removed;

  private ListDiff(List<T> added, List<T> removed) {
    this.added = added;
    this.removed = removed;
  }

}
