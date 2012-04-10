package joist.domain.validation.rules;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

public class NotEmpty<T extends DomainObject> implements Rule<T> {

  private final Shim<T, String> shim;

  public NotEmpty(Shim<T, String> shim) {
    this.shim = shim;
  }

  public void validate(ValidationErrors errors, T t) {
    String value = this.shim.get(t);
    if (value != null && value.trim().length() == 0) {
      errors.addPropertyError(t, this.shim.getName(), "cannot be empty");
    }
  }

}
