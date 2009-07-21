package joist.domain.validation.rules;

import joist.domain.DomainObject;
import joist.domain.validation.ValidationErrors;

import org.exigencecorp.bindgen.BindingRoot;

public class NotNull<T extends DomainObject> implements Rule<T> {

    private final BindingRoot<T, ?> binding;

    public NotNull(BindingRoot<T, ?> binding) {
        this.binding = binding;
    }

    public void validate(ValidationErrors errors, T t) {
        Object value = this.binding.getWithRoot(t);
        if (value == null) {
            errors.addPropertyError(t, this.binding.getName(), "is required");
        }
    }

}
