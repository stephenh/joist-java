package joist.domain.exceptions;

/** Exception denoting a domain object was not found. */
public class NotFoundException extends DomainObjectsException {

  private static final long serialVersionUID = 1L;

  public NotFoundException(Class<?> type) {
    super("Instance of " + type.getSimpleName() + " not found");
  }

  public NotFoundException(Class<?> type, long id) {
    super(type.getSimpleName() + "#" + id + " not found");
  }

}
