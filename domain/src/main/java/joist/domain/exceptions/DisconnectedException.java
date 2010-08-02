package joist.domain.exceptions;

public class DisconnectedException extends DomainObjectsException {

  private static final long serialVersionUID = 1L;

  public DisconnectedException() {
    super("The UoW is currently closed");
  }

}
