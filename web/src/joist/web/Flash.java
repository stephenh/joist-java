package joist.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Flash {

    private final Map<String, Object> contents = new HashMap<String, Object>();

    public void put(String key, Object value) {
        this.contents.put(key, value);
    }

    public Set<Entry<String, Object>> get() {
        return this.contents.entrySet();
    }

    public void clear() {
        this.contents.clear();
    }

}
