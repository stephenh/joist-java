package org.exigencecorp.domainobjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.ValidationErrors;
import org.exigencecorp.domainobjects.validation.errors.ValidationError;
import org.exigencecorp.domainobjects.validation.rules.Rule;
import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Inflector;
import org.exigencecorp.util.ToString;

public abstract class AbstractDomainObject implements DomainObject {

    private final List<Rule<?>> validationRules = new ArrayList<Rule<?>>();
    private final Set<String> changedProperties = new HashSet<String>();

    /** Used for determining whether we are dirty based our own primitive properties. */
    protected final void recordIfChanged(String property, Object oldValue, Object newValue) {
        if (!ObjectUtils.equals(oldValue, newValue)) {
            this.recordIfChanged(property);
        }
    }

    /** Used for determining whether we are dirty based on changes in our collections. */
    protected final void recordIfChanged(String property) {
        this.changedProperties.add(property);
        if (UoW.isOpen()) {
            UoW.getCurrent().getValidator().enqueue(this);
        }
    }

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

    public boolean isNew() {
        return this.getId() == null || this.getChangedProperties().contains("id");
    }

    public boolean isDirty() {
        return this.getChangedProperties().size() > 0;
    }

    public Set<String> getChangedProperties() {
        return this.changedProperties;
    }

    /** Called when the object was been successfully flushed to the database. */
    public void clearChangedProperties() {
        this.changedProperties.clear();
    }

    /** Stub for subclasses to override to update derived values as part of the UoW.flush() process. */
    public void updateDerivedValues() {
    }

    public void addRule(Rule<?> rule) {
        this.validationRules.add(rule);
    }

    public void removeRule(Rule<?> rule) {
        this.validationRules.remove(rule);
    }

    public List<Rule<?>> getValidationRules() {
        return Copy.shallow(this.validationRules);
    }

}
