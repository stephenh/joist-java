package joist.domain.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Wraps a list but allows hooking into add/remove operations.
 *
 * This class takes care of the tedious parts of implementing the full {@link List} interface,
 * for the most part deferring the backing list, but pushing modifications (adds/removes)
 * through the {@link ListProxy.Delegate}.
 *
 * This allows clients to focus on only the {@code doAdd}/{@code doRemove} logic.
 */
public class ListProxy<E> implements List<E> {

  /** An interface for the list owner to perform actual add/remove operations. */
  public interface Delegate<E> {
    /** Add {@code e} to the backing list {@code l}. */
    void doAdd(E e);

    /** Remove {@code e} from the backing list {@code l}. */
    void doRemove(E e);
  }

  private final List<E> l;
  private final Delegate<E> d;

  public ListProxy(List<E> l, Delegate<E> d) {
    this.l = l;
    this.d = d;
  }

  @Override
  public int size() {
    return this.l.size();
  }

  @Override
  public boolean isEmpty() {
    return this.l.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return this.l.contains(o);
  }

  @Override
  public Iterator<E> iterator() {
    final Iterator<E> real = this.l.iterator();
    return new Iterator<E>() {
      private E current;

      @Override
      public boolean hasNext() {
        return real.hasNext();
      }

      @Override
      public E next() {
        this.current = real.next();
        return this.current;
      }

      @Override
      public void remove() {
        // remove from the iterator first to avoid
        // ConcurrentModificationException when we
        // call our delegate
        real.remove();
        ListProxy.this.d.doRemove(this.current);
      }
    };
  }

  @Override
  public Object[] toArray() {
    return this.l.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return this.l.toArray(a);
  }

  @Override
  public boolean add(E e) {
    if (!this.l.contains(e)) {
      this.d.doAdd(e);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean remove(Object o) {
    // yeah, check + operation, but that's okay
    if (this.l.contains(o)) {
      this.d.doRemove((E) o);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return this.l.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    if (!this.containsAll(c)) {
      for (E e : c) {
        this.add(e);
      }
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    boolean removed = false;
    for (Object e : c) {
      if (this.contains(e)) {
        this.remove(e);
      }
    }
    return removed;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    boolean changed = false;
    for (Iterator<E> i = this.iterator(); i.hasNext();) {
      E e = i.next();
      if (!c.contains(e)) {
        i.remove();
        changed = true;
      }
    }
    return changed;
  }

  @Override
  public void clear() {
    for (Iterator<E> i = this.iterator(); i.hasNext();) {
      i.next();
      i.remove();
    }
  }

  @Override
  public E get(int index) {
    return this.l.get(index);
  }

  @Override
  public E set(int index, E element) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public void add(int index, E element) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public E remove(int index) {
    E e = this.get(index);
    this.remove(e);
    return e;
  }

  @Override
  public int indexOf(Object o) {
    return this.l.indexOf(o);
  }

  @Override
  public int lastIndexOf(Object o) {
    return this.l.lastIndexOf(o);
  }

  @Override
  public ListIterator<E> listIterator() {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException("Not implemented");
  }

}
