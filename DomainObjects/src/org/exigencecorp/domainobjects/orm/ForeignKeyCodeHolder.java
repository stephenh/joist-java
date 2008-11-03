package org.exigencecorp.domainobjects.orm;

import org.exigencecorp.domainobjects.Code;
import org.exigencecorp.domainobjects.util.Codes;

/** A value holder that will lazy load the foreign key that points to a code/enum. */
public class ForeignKeyCodeHolder<T extends Code> {

    private Class<T> codeClass;
    private Integer id;
    private T instance;

    public ForeignKeyCodeHolder(Class<T> codeClass) {
        this.codeClass = codeClass;
    }

    public T get() {
        if (this.instance == null && this.id != null) {
            this.instance = Codes.fromInt(this.codeClass, this.id);
        }
        return this.instance;
    }

    public void set(T instance) {
        this.instance = instance;
        if (instance == null) {
            this.id = null;
        }
    }

    public Integer getId() {
        if (this.instance != null) {
            return this.instance.getId().intValue();
        }
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
