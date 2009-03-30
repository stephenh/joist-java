package joist.domain.validation.rules;

import joist.domain.AbstractDomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

public class NotNull<T extends AbstractDomainObject> implements Rule<T> {

    private final Shim<T, ?> shim;

    public NotNull(Shim<T, ?> shim) {
        this.shim = shim;
    }

    public void validate(ValidationErrors errors, T t) {
        Object value = this.shim.get(t);
        if (value == null) {
            errors.addPropertyError(t, this.shim.getName(), "is required");
        }
    }

}
