package joist.domain.validation.rules;

import joist.domain.AbstractDomainObject;
import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

import org.apache.commons.lang.StringUtils;

public class MustBeAlphanumeric<T extends DomainObject> implements Rule<T> {

  private final Shim<T, String> shim;

  public static <T extends AbstractDomainObject> MustBeAlphanumeric<T> rule(Shim<T, String> shim) {
    return new MustBeAlphanumeric<T>(shim);
  }

  public MustBeAlphanumeric(Shim<T, String> shim) {
    this.shim = shim;
  }

  public void validate(ValidationErrors errors, T t) {
    String value = this.shim.get(t);
    if (value != null && !StringUtils.isAlphanumeric(value)) {
      errors.addPropertyError(t, this.shim.getName(), "must be alphanumeric");
    }
  }

}
