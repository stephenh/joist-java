package org.exigencecorp.domainobjects.orm;

import java.util.HashMap;
import java.util.Map;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.util.Log;

public class IdentityMap {

    private final Map<String, DomainObject> objects = new HashMap<String, DomainObject>();

    public void store(DomainObject o) {
        Class<?> rootType = AliasRegistry.getRootClass(o.getClass());
        Integer id = o.getId().intValue();
        Log.trace("Storing {}#{} in identity map", rootType, id);
        if (this.objects.put(rootType + "#" + id, o) != null) {
            throw new RuntimeException("Domain object conflicts with an existing id " + o);
        }
    }

    public Object findOrNull(Class<?> type, Integer id) {
        Class<?> rootType = AliasRegistry.getRootClass(type);
        Object o = this.objects.get(rootType + "#" + id);
        if (o != null) {
            Log.trace("Found {}#{} in identity map", rootType, id);
            return o;
        }
        Log.trace("Missed {}#{} in identity map", rootType, id);
        return null;
    }

    public int size() {
        return this.objects.size();
    }

}
