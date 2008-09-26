package org.exigencecorp.domainobjects.validation.rules;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.validation.ValidationErrors;

public class NotNull<T extends AbstractDomainObject> implements Rule<T> {

    private String property;

    public NotNull(String property) {
        this.property = property;
    }

    public void validateObject(ValidationErrors errors, T t) {
        Object value = null; // Ognl.get(t, this.property, Object.class);
        if (value == null) {
            errors.addPropertyError(t, this.property, "is required");
        }
    }

}
