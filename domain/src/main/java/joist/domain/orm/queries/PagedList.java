package joist.domain.orm.queries;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import joist.domain.DomainObject;

public class PagedList<T extends DomainObject> implements List<T> {

  private Select<T> query;
  private String message = "PagedList only supports size() and subList(int,int)";
  private Integer cachedCount;

  public PagedList(Select<T> query) {
    this.query = query;
  }

  public int size() {
    if (this.cachedCount == null) {
      this.cachedCount = (int) this.query.count();
    }
    return this.cachedCount;
  }

  public List<T> subList(int fromIndex, int toIndex) {
    this.query.offset(fromIndex);
    this.query.limit(toIndex - fromIndex);
    return this.query.list();
  }

  public boolean add(T e) {
    throw new RuntimeException(this.message);
  }

  public void add(int index, T element) {
    throw new RuntimeException(this.message);
  }

  public boolean addAll(Collection<? extends T> c) {
    throw new RuntimeException(this.message);
  }

  public boolean addAll(int index, Collection<? extends T> c) {
    throw new RuntimeException(this.message);
  }

  public void clear() {
    throw new RuntimeException(this.message);
  }

  public boolean contains(Object o) {
    throw new RuntimeException(this.message);
  }

  public boolean containsAll(Collection<?> c) {
    throw new RuntimeException(this.message);
  }

  public T get(int index) {
    throw new RuntimeException(this.message);
  }

  public int indexOf(Object o) {
    throw new RuntimeException(this.message);
  }

  public boolean isEmpty() {
    throw new RuntimeException(this.message);
  }

  public Iterator<T> iterator() {
    throw new RuntimeException(this.message);
  }

  public int lastIndexOf(Object o) {
    throw new RuntimeException(this.message);
  }

  public ListIterator<T> listIterator() {
    throw new RuntimeException(this.message);
  }

  public ListIterator<T> listIterator(int index) {
    throw new RuntimeException(this.message);
  }

  public boolean remove(Object o) {
    throw new RuntimeException(this.message);
  }

  public T remove(int index) {
    throw new RuntimeException(this.message);
  }

  public boolean removeAll(Collection<?> c) {
    throw new RuntimeException(this.message);
  }

  public boolean retainAll(Collection<?> c) {
    throw new RuntimeException(this.message);
  }

  public T set(int index, T element) {
    throw new RuntimeException(this.message);
  }

  public Object[] toArray() {
    throw new RuntimeException(this.message);
  }

  public <U> U[] toArray(U[] a) {
    throw new RuntimeException(this.message);
  }

}
