package org.exigencecorp.domainobjects;

import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.validation.ValidationErrors;
import org.exigencecorp.domainobjects.validation.errors.ValidationError;
import org.exigencecorp.domainobjects.validation.rules.Rule;
import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Inflector;
import org.exigencecorp.util.ToString;

public abstract class AbstractDomainObject implements DomainObject {

    private final List<Rule<?>> validationRules = new ArrayList<Rule<?>>();

    public final List<ValidationError> validate() {
        ValidationErrors errors = new ValidationErrors();
        for (Rule<?> rule : this.getValidationRules()) {
            ((Rule<AbstractDomainObject>) rule).validateObject(errors, this);
        }
        return errors.getErrors();
    }

    public String toFriendlyTypeName() {
        return Inflector.humanize(this.getClass().getSimpleName());
    }

    public String toFriendlyString() {
        return this.toString();
    }

    public String toString() {
        return ToString.to(this, this.getId());
    }

    public final boolean isNew() {
        return this.getId() == null || this.getChanged().contains("id");
    }

    public final boolean isDirty() {
        return this.getChanged().size() > 0;
    }

    public final void addRule(Rule<?> rule) {
        this.validationRules.add(rule);
    }

    public final void removeRule(Rule<?> rule) {
        this.validationRules.remove(rule);
    }

    public final List<Rule<?>> getValidationRules() {
        return Copy.shallow(this.validationRules);
    }

    public void updateDerivedValues() {
    }

}
