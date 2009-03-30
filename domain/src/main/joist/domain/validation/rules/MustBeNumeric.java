package joist.domain.validation.rules;

import joist.domain.AbstractDomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

import org.apache.commons.lang.StringUtils;

public class MustBeNumeric<T extends AbstractDomainObject> implements Rule<T> {

    private final String property;
    private final Shim<T, String> shim;

    public MustBeNumeric(String property, Shim<T, String> shim) {
        this.property = property;
        this.shim = shim;
    }

    public void validate(ValidationErrors errors, T t) {
        String value = this.shim.get(t);
        if (value != null && !StringUtils.isNumeric(value)) {
            errors.addPropertyError(t, this.property, "must be numeric");
        }
    }

}
