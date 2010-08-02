package joist.domain.validation.rules;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

public class MaxLength<T extends DomainObject> implements Rule<T> {

  private final Shim<T, String> shim;
  private final int length;

  public MaxLength(Shim<T, String> shim, int length) {
    this.length = length;
    this.shim = shim;
  }

  public void validate(ValidationErrors errors, T t) {
    String value = this.shim.get(t);
    if (value != null && value.length() > this.length) {
      errors.addPropertyError(t, this.shim.getName(), "must be no more than {} character{}", this.length, (this.length == 1 ? "" : "s"));
    }
  }

  public String getProperty() {
    return this.shim.getName();
  }

  public int getLength() {
    return this.length;
  }

}
