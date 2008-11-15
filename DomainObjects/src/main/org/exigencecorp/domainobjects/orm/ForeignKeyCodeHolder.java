package org.exigencecorp.domainobjects.orm;

import org.exigencecorp.domainobjects.Code;
import org.exigencecorp.domainobjects.util.Codes;

/** A value holder that will lazy load the foreign key that points to a code/enum. */
public class ForeignKeyCodeHolder<T extends Enum<T>> {

    private Class<T> codeClass;
    private T instance;

    public ForeignKeyCodeHolder(Class<T> codeClass) {
        this.codeClass = codeClass;
    }

    public T get() {
        return this.instance;
    }

    public void set(T instance) {
        this.instance = instance;
    }

    public Integer getId() {
        return ((Code) this.instance).getId();
    }

    public void setId(Integer id) {
        this.instance = Codes.fromInt(this.codeClass, id);
    }

}
