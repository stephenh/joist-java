package joist.domain.exceptions;

import joist.domain.DomainObject;

public class OpLockException extends DomainObjectsException {

  private static final long serialVersionUID = 1L;

  public OpLockException(DomainObject instance) {
    super("Op lock failed for " + instance);
  }

}
