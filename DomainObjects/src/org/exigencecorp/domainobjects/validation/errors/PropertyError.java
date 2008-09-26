package org.exigencecorp.domainobjects.validation.errors;

import org.exigencecorp.util.FriendlyString;
import org.exigencecorp.util.Inflector;

/** An error that applies to just a property. */
public class PropertyError implements ValidationError {

    private Object parent;
    private String property;
    private String message;

    public PropertyError(Object parent, String property, String message) {
        this.parent = parent;
        this.property = property;
        this.message = message;
    }

    public Object getParent() {
        return this.parent;
    }

    public String getMessage() {
        return Inflector.humanize(this.property) + " " + this.message;
    }

    public String toString() {
        return FriendlyString.withoutTypeNames(this, "{} (on {} {})", "message", this.parent.toString(), "parent");
    }

}
