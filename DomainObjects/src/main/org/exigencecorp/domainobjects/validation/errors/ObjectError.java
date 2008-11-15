package org.exigencecorp.domainobjects.validation.errors;

import org.exigencecorp.domainobjects.AbstractDomainObject;

/** An error that applies to the whole object. */
public class ObjectError implements ValidationError {

    private AbstractDomainObject ado;
    private String message;

    public ObjectError(AbstractDomainObject ado, String message) {
        this.ado = ado;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return this.message + " (on " + this.ado.toString() + " " + this.ado.toFriendlyString() + ")";
    }

}
