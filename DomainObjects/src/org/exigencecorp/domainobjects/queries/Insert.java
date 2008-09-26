package org.exigencecorp.domainobjects.queries;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;

public class Insert<T extends DomainObject> {

    public static <T extends DomainObject> Insert<T> into(Alias<T> alias) {
        return new Insert<T>(alias);
    }

    private final Alias<T> alias;
    private final List<SetItem> setItems = new ArrayList<SetItem>();

    private Insert(Alias<T> alias) {
        this.alias = alias;
    }

    public void set(SetItem setItem) {
        this.setItems.add(setItem);
    }

    public Alias<T> getAlias() {
        return this.alias;
    }

    public List<SetItem> getSetItems() {
        return this.setItems;
    }

}
