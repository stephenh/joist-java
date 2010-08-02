package joist.domain.exceptions;

public class TooManyException extends DomainObjectsException {

  private static final long serialVersionUID = 1L;

  public TooManyException() {
    super("Too many");
  }

}
