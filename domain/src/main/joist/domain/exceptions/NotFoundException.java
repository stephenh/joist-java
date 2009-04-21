package joist.domain.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends DomainObjectsException {

    public NotFoundException() {
        super("Not found");
    }

}
