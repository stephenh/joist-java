package joist.codegen;

import java.util.ArrayList;
import java.util.List;

/**
 * Keeps track of what values have been checked via {@code get} calls.
 *
 * Primarily used to detect out-of-date config settings.
 */
public class MarkedList<T> extends ArrayList<T> {

  private static final long serialVersionUID = 1L;
  private final List<T> checked = new ArrayList<T>();

  @Override
  @SuppressWarnings("unchecked")
  public boolean contains(Object object) {
    this.checked.add((T) object);
    return super.contains(object);
  }

  public List<T> getStaleValues() {
    List<T> copy = new ArrayList<T>(this);
    copy.removeAll(this.checked);
    return copy;
  }

}
