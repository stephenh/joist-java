package org.exigencecorp.domainobjects.validation.rules;

import java.util.regex.Pattern;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.Requirements;
import org.exigencecorp.domainobjects.Shim;
import org.exigencecorp.domainobjects.validation.ValidationErrors;

public class RegexRule<T extends AbstractDomainObject> implements Rule<T> {

    static {
        Requirements.rulesCanBeRegex.fulfills();
    }

    private final String property;
    private final Shim<T, String> shim;
    private final String pattern;

    public RegexRule(String property, String pattern, Shim<T, String> shim) {
        this.property = property;
        this.pattern = pattern;
        this.shim = shim;
    }

    public void validateObject(ValidationErrors errors, T t) {
        String value = (this.shim != null) ? this.shim.get(t) : null;
        if (value != null && !Pattern.matches(this.pattern, value)) {
            errors.addPropertyError(t, this.property, "is invalid");
        }
    }

    public String getProperty() {
        return this.property;
    }

}
