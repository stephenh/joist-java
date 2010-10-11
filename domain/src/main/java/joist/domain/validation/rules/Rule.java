package joist.domain.validation.rules;

import joist.domain.validation.ValidationErrors;

public interface Rule<T> {

  void validate(ValidationErrors errors, T object);

}
