package joist.util;

import java.util.HashMap;

/** Adds fluent helper methods to HashMap.
 *
 * Should probably be an interface.
 */
public class FluentMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    public FluentMap<K, V> with(K key, V value) {
        this.put(key, value);
        return this;
    }

}
