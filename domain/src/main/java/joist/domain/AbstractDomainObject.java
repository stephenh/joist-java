package joist.domain;

import java.util.ArrayList;
import java.util.List;

import joist.domain.validation.ValidationErrors;
import joist.domain.validation.errors.ValidationError;
import joist.domain.validation.rules.Rule;
import joist.util.Inflector;
import joist.util.ToString;

public abstract class AbstractDomainObject implements DomainObject {

  public static void setFromSnapshot(DomainObject instance) {
    ((AbstractDomainObject) instance).isFromSnapshot = true;
  }

  public static boolean isFromSnapshot(DomainObject instance) {
    return ((AbstractDomainObject) instance).isFromSnapshot;
  }

  private final List<Rule<?>> validationRules = new ArrayList<Rule<?>>();
  private boolean isFromSnapshot;
  private boolean wasUpdateDerivedValuesCalled = false;

  public final List<ValidationError> validate() {
    ValidationErrors errors = new ValidationErrors();
    for (Rule<?> rule : this.validationRules) {
      ((Rule<AbstractDomainObject>) rule).validate(errors, this);
    }
    return errors.getErrors();
  }

  public String toTextTypeName() {
    return Inflector.humanize(this.getClass().getSimpleName());
  }

  public String toTextString() {
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

  public void clearAssociations() {
  }

  public void updateDerivedValues() {
    this.wasUpdateDerivedValuesCalled = true;
  }

  // should really only be visible to Validator
  @Override
  public boolean wasUpdateDerivedValuesCalled() {
    return this.wasUpdateDerivedValuesCalled;
  }

  @Override
  public void resetWasUpdateDerivedValuesCalled() {
    this.wasUpdateDerivedValuesCalled = false;
  }
}
