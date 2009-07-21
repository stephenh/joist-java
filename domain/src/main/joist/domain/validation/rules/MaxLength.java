package joist.domain.validation.rules;

import joist.domain.DomainObject;
import joist.domain.validation.ValidationErrors;

import org.exigencecorp.bindgen.BindingRoot;

public class MaxLength<T extends DomainObject> implements Rule<T> {

    private final BindingRoot<T, String> binding;
    private final int length;

    public MaxLength(BindingRoot<T, String> binding, int length) {
        this.length = length;
        this.binding = binding;
    }

    public void validate(ValidationErrors errors, T t) {
        String value = this.binding.getWithRoot(t);
        if (value != null && value.length() > this.length) {
            errors.addPropertyError(t, this.binding.getName(), "must be no more than {} character{}", this.length, (this.length == 1 ? "" : "s"));
        }
    }

    public String getProperty() {
        return this.binding.getName();
    }

    public int getLength() {
        return this.length;
    }

}
