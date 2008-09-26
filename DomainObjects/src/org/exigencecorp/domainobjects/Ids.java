package org.exigencecorp.domainobjects;

import java.util.ArrayList;
import java.util.List;

public class Ids<T extends DomainObject> {

    private final List<Id<T>> ids = new ArrayList<Id<T>>();

    public Ids() {
    }

    public Ids(Class<T> type, List<Integer> ids) {
        for (Integer id : ids) {
            this.ids.add(new Id<T>(type, id));
        }
    }

    public String toString() {
        return this.ids.toString();
    }

    public List<Id<T>> getIds() {
        return this.ids;
    }

}
