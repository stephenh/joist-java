package joist.domain.exceptions;

import java.util.List;

/** Exception noting too many domain objects were found when only 1 was expected. */
public class TooManyException extends DomainObjectsException {

  private static final long serialVersionUID = 1L;

  public <T> TooManyException(Class<T> type, List<T> instances) {
    super("Too many " + type.getSimpleName() + " found: " + instances);
  }

}
