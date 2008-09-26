package org.exigencecorp.domainobjects.validation.rules;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.validation.ValidationErrors;

public class MaxLength<T extends AbstractDomainObject> implements Rule<T> {

    private String property;
    private int length;

    public MaxLength(String property, int length) {
        this.property = property;
        this.length = length;
    }

    public void validateObject(ValidationErrors errors, T t) {
        String value = null; // Ognl.get(t, this.getProperty(), String.class);
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
