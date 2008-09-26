package org.exigencecorp.domainobjects;

import java.util.ArrayList;
import java.util.List;

public class Ids<T extends DomainObject> {

    private final Class<T> type;
    private final List<Id<T>> ids = new ArrayList<Id<T>>();

    public Ids(Class<T> type) {
        this.type = type;
    }

    public Ids(Class<T> type, List<Integer> ids) {
        this.type = type;
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

    public int size() {
        return this.ids.size();
    }

    public void add(Integer id) {
        this.ids.add(new Id<T>(this.type, id));
    }

}
