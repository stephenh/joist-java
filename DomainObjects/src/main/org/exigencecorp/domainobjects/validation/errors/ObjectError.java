package org.exigencecorp.domainobjects.validation.errors;

import org.exigencecorp.domainobjects.DomainObject;

/** An error that applies to the whole object. */
public class ObjectError implements ValidationError {

    private DomainObject instance;
    private String message;

    public ObjectError(DomainObject instance, String message) {
        this.instance = instance;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return this.message + " - " + this.instance;
    }

    public DomainObject getInstance() {
        return this.instance;
    }

}
