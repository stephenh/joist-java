package joist.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class Make {

    public static <V> FluentList<V> list(V value) {
        return new FluentList<V>().with(value);
    }

    public static <V> FluentList<V> list(List<V> values) {
        return new FluentList<V>().with(values);
    }

    public static class FluentList<V> implements List<V> {
        private final List<V> list = new ArrayList<V>();

        // Fluent methods

        public FluentList<V> with(V value) {
            this.add(value);
            return this;
        }

        public FluentList<V> with(List<V> values) {
            this.addAll(values);
            return this;
        }

        // List methods

        public boolean add(V e) {
            return this.list.add(e);
        }

        public void add(int index, V element) {
            this.list.add(index, element);
        }

        public boolean addAll(Collection<? extends V> c) {
            return this.list.addAll(c);
        }

        public boolean addAll(int index, Collection<? extends V> c) {
            return this.list.addAll(index, c);
        }

        public void clear() {
            this.list.clear();
        }

        public boolean contains(Object o) {
            return this.list.contains(o);
        }

        public boolean containsAll(Collection<?> c) {
            return this.list.containsAll(c);
        }

        public V get(int index) {
            return this.list.get(index);
        }

        public int indexOf(Object o) {
            return this.list.indexOf(o);
        }

        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        public Iterator<V> iterator() {
            return this.list.iterator();
        }

        public int lastIndexOf(Object o) {
            return this.list.lastIndexOf(o);
        }

        public ListIterator<V> listIterator() {
            return this.list.listIterator();
        }

        public ListIterator<V> listIterator(int index) {
            return this.list.listIterator(index);
        }

        public boolean remove(Object o) {
            return this.list.remove(o);
        }

        public V remove(int index) {
            return this.list.remove(index);
        }

        public boolean removeAll(Collection<?> c) {
            return this.list.removeAll(c);
        }

        public boolean retainAll(Collection<?> c) {
            return this.list.retainAll(c);
        }

        public V set(int index, V element) {
            return this.list.set(index, element);
        }

        public int size() {
            return this.list.size();
        }

        public List<V> subList(int fromIndex, int toIndex) {
            return this.list.subList(fromIndex, toIndex);
        }

        public Object[] toArray() {
            return this.list.toArray();
        }

        public <T> T[] toArray(T[] a) {
            return this.list.toArray(a);
        }
    }

    public static <K, V> FluentMap<K, V> map(K key, V value) {
        return new FluentMap<K, V>().add(key, value);
    }

    public static class FluentMap<K, V> implements Map<K, V> {
        private final Map<K, V> map = new HashMap<K, V>();

        // Fluent methods

        public FluentMap<K, V> add(K key, V value) {
            this.map.put(key, value);
            return this;
        }

        // Map methods

        public V put(K key, V value) {
            return this.map.put(key, value);
        }

        public void clear() {
            this.map.clear();
        }

        public boolean containsKey(Object key) {
            return this.map.containsKey(key);
        }

        public boolean containsValue(Object value) {
            return this.map.containsValue(value);
        }

        public Set<java.util.Map.Entry<K, V>> entrySet() {
            return this.map.entrySet();
        }

        public V get(Object key) {
            return this.map.get(key);
        }

        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        public Set<K> keySet() {
            return this.map.keySet();
        }

        public void putAll(Map<? extends K, ? extends V> m) {
            this.map.putAll(m);
        }

        public V remove(Object key) {
            return this.map.remove(key);
        }

        public int size() {
            return this.map.size();
        }

        public Collection<V> values() {
            return this.map.values();
        }
    }

}
