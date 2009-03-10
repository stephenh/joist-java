package joist.domain.validation.rules;


import joist.domain.AbstractDomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

public class NotNull<T extends AbstractDomainObject> implements Rule<T> {

    private final String property;
    private final Shim<T, ?> shim;

    public NotNull(String property) {
        this.property = property;
        this.shim = null;
    }

    public NotNull(String property, Shim<T, ?> shim) {
        this.property = property;
        this.shim = shim;
    }

    public void validate(ValidationErrors errors, T t) {
        Object value = (this.shim != null) ? this.shim.get(t) : null;
        if (value == null) {
            errors.addPropertyError(t, this.property, "is required");
        }
    }

}
