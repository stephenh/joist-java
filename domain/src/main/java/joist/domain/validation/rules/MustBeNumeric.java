package joist.domain.validation.rules;

import joist.domain.AbstractDomainObject;
import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

import org.apache.commons.lang.StringUtils;

public class MustBeNumeric<T extends DomainObject> implements Rule<T> {

  private final Shim<T, String> shim;

  public static <T extends AbstractDomainObject> MustBeNumeric<T> rule(Shim<T, String> shim) {
    return new MustBeNumeric<T>(shim);
  }

  public MustBeNumeric(Shim<T, String> shim) {
    this.shim = shim;
  }

  public void validate(ValidationErrors errors, T t) {
    String value = this.shim.get(t);
    if (value != null && !StringUtils.isNumeric(value)) {
      errors.addPropertyError(t, this.shim.getName(), "must be numeric");
    }
  }

}
