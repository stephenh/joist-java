package joist.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Make {

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
