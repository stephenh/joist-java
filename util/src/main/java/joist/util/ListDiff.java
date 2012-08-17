package joist.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListDiff<T> {

  /**
   * Diffs {@code original} and {@code updated}.
   *
   * Note that, despite the {@code Collection} types, this generally assumes both
   * original and updated are sets, because it was originally written for managing
   * foreign keys of domain objects, where a child is not listed twice.
   */
  public static <T> ListDiff<T> of(Collection<T> original, Collection<T> updated) {
    List<T> added = new ArrayList<T>();
    List<T> removed = new ArrayList<T>();
    if (original == null && updated == null) {
      // do nothing
    } else if (original != null && updated == null) {
      removed.addAll(original);
    } else if (original == null && updated != null) {
      added.addAll(updated);
    } else {
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
