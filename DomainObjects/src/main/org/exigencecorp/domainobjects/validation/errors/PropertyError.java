package org.exigencecorp.domainobjects.validation.errors;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.util.Inflector;

/** An error that applies to just a property. */
public class PropertyError implements ValidationError {

    private final DomainObject instance;
    private final String property;
    private final String message;

    public PropertyError(DomainObject instance, String property, String message) {
        this.instance = instance;
        this.property = property;
        this.message = message;
    }

    public DomainObject getInstance() {
        return this.instance;
    }

    public String getMessage() {
        return Inflector.humanize(this.property) + " " + this.message;
    }

    public String toString() {
        return this.getMessage() + " - " + this.instance;
    }

}
