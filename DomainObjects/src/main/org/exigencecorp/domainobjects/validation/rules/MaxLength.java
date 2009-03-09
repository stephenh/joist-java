package org.exigencecorp.domainobjects.validation.rules;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.validation.ValidationErrors;

public class MaxLength<T extends AbstractDomainObject> implements Rule<T> {

    private final String property;
    private final Shim<T, String> shim;
    private final int length;

    public MaxLength(String property, int length) {
        this.property = property;
        this.length = length;
        this.shim = null;
    }

    public MaxLength(String property, int length, Shim<T, String> shim) {
        this.property = property;
        this.length = length;
        this.shim = shim;
    }

    public void validate(ValidationErrors errors, T t) {
        String value = (this.shim != null) ? this.shim.get(t) : null;
        if (value != null && value.length() > this.length) {
            errors.addPropertyError(t, this.property, "must be no more than {} character{}", this.length, (this.length == 1 ? "" : "s"));
        }
    }

    public String getProperty() {
        return this.property;
    }

    public int getLength() {
        return this.length;
    }

}
