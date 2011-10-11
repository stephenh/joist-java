package joist.domain.validation.rules;

import java.util.regex.Pattern;

import joist.domain.DomainObject;
import joist.domain.Shim;
import joist.domain.validation.ValidationErrors;

public class RegexRule<T extends DomainObject> implements Rule<T> {

  private final String property;
  private final Shim<T, String> shim;
  private final String pattern;

  public RegexRule(String property, String pattern, Shim<T, String> shim) {
    this.property = property;
    this.pattern = pattern;
    this.shim = shim;
  }

  public void validate(ValidationErrors errors, T t) {
    String value = (this.shim != null) ? this.shim.get(t) : null;
    if (value != null && !Pattern.matches(this.pattern, value)) {
      errors.addPropertyError(t, this.property, "is invalid");
    }
  }

  public String getProperty() {
    return this.property;
  }

}
