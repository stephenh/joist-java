package joist.domain.exceptions;

import java.util.Collection;

/** Exception denoting a domain object was not found. */
public class NotFoundException extends DomainObjectsException {

  private static final long serialVersionUID = 1L;

  public NotFoundException(Class<?> type) {
    super("Instance of " + type.getSimpleName() + " not found");
  }

  public NotFoundException(Class<?> type, long id) {
    super(type.getSimpleName() + "#" + id + " not found");
  }

  public NotFoundException(Class<?> type, Collection<Long> ids) {
    super(type.getSimpleName() + " id in (" + String.join(", ", (String[]) ids.stream().map(id -> String.valueOf(id)).toArray()) + ") not found");
  }

}
