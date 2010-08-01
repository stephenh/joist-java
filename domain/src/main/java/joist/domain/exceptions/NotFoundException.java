package joist.domain.exceptions;

public class NotFoundException extends DomainObjectsException {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("Not found");
    }

}
