package joist.domain.exceptions;

@SuppressWarnings("serial")
public class DomainObjectsException extends RuntimeException {

  protected DomainObjectsException(String message) {
    super(message);
  }

}
