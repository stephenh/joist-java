package org.exigencecorp.domainobjects.queries;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.uow.UoW;

public class Update<T extends DomainObject> {

    private final Alias<T> alias;
    private final List<SetItem> setItems = new ArrayList<SetItem>();
    private Where where = null;

    private Update(Alias<T> alias) {
        this.alias = alias;
    }

    public static <T extends DomainObject> Update<T> into(Alias<T> alias) {
        return new Update<T>(alias);
    }

    public void update() {
    }

    public void execute() {
        UoW.getCurrent().getRepository().update(this);
    }

    public void set(SetItem setItem) {
        this.setItems.add(setItem);
    }

    public void where(Where where) {
        if (this.where != null) {
            throw new RuntimeException("Already set");
        }
        this.where = where;
    }

    public Alias<T> getAlias() {
        return this.alias;
    }

    public List<SetItem> getSetItems() {
        return this.setItems;
    }

    public Where getWhere() {
        return this.where;
    }

}
