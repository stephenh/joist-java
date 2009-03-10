package joist.domain.exceptions;

@SuppressWarnings("serial")
public class TooManyException extends DomainObjectsException {

    public TooManyException() {
        super("Too many");
    }

}
