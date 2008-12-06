package org.exigencecorp.domainobjects.exceptions;

@SuppressWarnings("serial")
public class TooManyException extends DomainObjectsException {

    public TooManyException() {
        super("Too many");
    }

}
