package joist.domain.validation;

import java.util.ArrayList;
import java.util.List;

import joist.domain.DomainObject;
import joist.domain.util.converters.TextString;
import joist.domain.validation.errors.GeneralError;
import joist.domain.validation.errors.ObjectError;
import joist.domain.validation.errors.PropertyError;
import joist.domain.validation.errors.ValidationError;

public class ValidationErrors {

  private List<ValidationError> errors = new ArrayList<ValidationError>();

  public void addError(String error) {
    this.errors.add(new GeneralError(error));
  }

  public void addObjectError(DomainObject o, String error, Object... args) {
    this.errors.add(new ObjectError(o, TextString.interpolate(error, args)));
  }

  public void addPropertyError(DomainObject o, String propertyName, String error, Object... args) {
    this.errors.add(new PropertyError(o, propertyName, TextString.interpolate(error, args)));
  }

  public List<ValidationError> getErrors() {
    return this.errors;
  }

}
