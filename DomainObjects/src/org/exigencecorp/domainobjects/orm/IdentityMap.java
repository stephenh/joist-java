package org.exigencecorp.domainobjects.orm;

import java.util.HashMap;
import java.util.Map;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.util.Log;

public class IdentityMap {

    private final Map<String, DomainObject> objects = new HashMap<String, DomainObject>();

    public void store(Class<?> type, DomainObject o) {
        Integer id = o.getId().intValue();
        Log.trace("Storing {}#{} in identity map", type, id);
        if (this.objects.put(type + "#" + id, o) != null) {
            throw new RuntimeException("Domain object conflicts with an existing id " + o);
        }
    }

    public Object findOrNull(Class<?> type, Integer id) {
        Object o = this.objects.get(type + "#" + id);
        if (o != null) {
            Log.trace("Found {}#{} in identity map", type, id);
            return o;
        }
        Log.trace("Missed {}#{} in identity map", type, id);
        return null;
    }

}
