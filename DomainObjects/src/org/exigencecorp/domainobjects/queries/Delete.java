package org.exigencecorp.domainobjects.queries;

import org.exigencecorp.domainobjects.DomainObject;

public class Delete<T extends DomainObject> {

    public static <T extends DomainObject> Delete<T> from(Alias<T> alias) {
        return new Delete<T>(alias);
    }

    private final Alias<T> alias;
    private Where where = null;

    private Delete(Alias<T> alias) {
        this.alias = alias;
    }

    public Alias<T> getAlias() {
        return this.alias;
    }

    public void where(Where where) {
        if (this.where != null) {
            throw new RuntimeException("Already set");
        }
        this.where = where;
    }

    public Where getWhere() {
        return this.where;
    }

}
