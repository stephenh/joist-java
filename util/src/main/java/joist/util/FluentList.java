package joist.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/** Adds fluent helper methods to ArrayList.
 *
 * Should probably be an interface.
 */
public class FluentList<V> extends ArrayList<V> {

  private static final long serialVersionUID = 1L;

  public FluentList() {
  }

  public FluentList(int initialCapacity) {
    super(initialCapacity);
  }

  public FluentList(Collection<V> values) {
    super(values);
  }

  public FluentList<V> with(V value) {
    this.add(value);
    return this;
  }

  public FluentList<V> with(V... values) {
    this.addAll(Arrays.asList(values));
    return this;
  }

  public FluentList<V> with(Collection<V> values) {
    this.addAll(values);
    return this;
  }

  public FluentList<V> reverse() {
    Collections.reverse(this);
    return this;
  }

  public FluentList<V> unique() {
    List<V> original = Copy.list(this);
    this.clear();
    for (V o : original) {
      if (!this.contains(o)) {
        this.add(o);
      }
    }
    return this;
  }

  public <V2> FluentList<V2> map(Function1<V2, V> f) {
    FluentList<V2> l = new FluentList<V2>(this.size());
    for (V value : this) {
      l.add(f.apply(value));
    }
    return l;
  }

}
