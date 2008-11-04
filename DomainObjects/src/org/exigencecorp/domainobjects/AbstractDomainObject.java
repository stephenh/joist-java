package org.exigencecorp.domainobjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.validation.ValidationErrors;
import org.exigencecorp.domainobjects.validation.errors.ValidationError;
import org.exigencecorp.domainobjects.validation.rules.Rule;
import org.exigencecorp.util.Copy;
import org.exigencecorp.util.Inflector;
import org.exigencecorp.util.ToString;

public abstract class AbstractDomainObject implements DomainObject {

    private List<Rule<?>> validationRules = new ArrayList<Rule<?>>();
    private List<String> changedProperties = new ArrayList<String>();

    /** Used for determining whether we are dirty based our own primitive properties. */
    protected final boolean recordIfChanged(String property, Object oldValue, Object newValue) {
        boolean changed = !ObjectUtils.equals(oldValue, newValue);
        if (changed && !this.changedProperties.contains(property)) {
            this.changedProperties.add(property);
        }
        // Properties, even if "unchanged", should still cause validation to happen against us later
        if (UoW.isOpen()) {
            UoW.getCurrent().getValidator().enqueue(this);
        }
        return changed;
    }

    /** Used for determining whether we are dirty based on primitives we cause to change in other people's primitives. */
    protected final void recordIfChanged(String property) {
        this.changedProperties.add(property);
        if (UoW.isOpen()) {
            UoW.getCurrent().getValidator().enqueue(this);
        }
    }

    public List<ValidationError> validate() {
        ValidationErrors errors = new ValidationErrors();
        for (Rule<?> rule : this.getValidationRules()) {
            ((Rule<AbstractDomainObject>) rule).validateObject(errors, this);
        }
        return errors.getErrors();
    }

    public String toTypeName() {
        return Inflector.humanize(this.getClass().getSimpleName());
    }

    public String toFriendlyStringWithTypeNamePrefixed() {
        return StringUtils.trim(this.toTypeName() + " " + this.toFriendlyString());
    }

    public String toFriendlyString() {
        return this.toString();
    }

    public String toString() {
        return ToString.to(this, this.getId());
    }

    public boolean isNew() {
        return this.getId() == null || this.getChangedProperties().indexOf("id") > -1;
    }

    public boolean isDirty() {
        return this.getChangedProperties().size() > 0;
    }

    public List<String> getChangedProperties() {
        return this.changedProperties;
    }

    /** Called when the object was been successfully flushed to the database. */
    public void clearChangedProperties() {
        this.changedProperties.clear();
    }

    /** Placeholder for subclasses to override, yet still always be able to call super.clearOwnerAssociations() with/without base classes. */
    public void clearOwnerAndNeitherAssociations() {
    }

    /** Placeholder for subclasses to override to update (potentially persistent) derived values as part of the UoW.flush() process. */
    public void updateDerivedValues() {
    }

    /** Placeholder for subclasses to clone themselves in a inheritance-safe manner. */
    protected void cloneWithPrimitivesAndCodesOnly(AbstractDomainObject instance) {
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
