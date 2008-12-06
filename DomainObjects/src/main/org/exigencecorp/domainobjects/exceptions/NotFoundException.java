package org.exigencecorp.domainobjects.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends DomainObjectsException {

    public NotFoundException() {
        super("Not found");
    }

}
