package org.exigencecorp.domainobjects.validation;


import java.util.ArrayList;
import java.util.List;

import org.exigencecorp.domainobjects.AbstractDomainObject;
import org.exigencecorp.domainobjects.validation.errors.GeneralError;
import org.exigencecorp.domainobjects.validation.errors.ObjectError;
import org.exigencecorp.domainobjects.validation.errors.PropertyError;
import org.exigencecorp.domainobjects.validation.errors.ValidationError;
import org.exigencecorp.util.FriendlyString;

public class ValidationErrors {

    private static final long serialVersionUID = 1;
    private List<ValidationError> errors = new ArrayList<ValidationError>();

    public void addError(String error) {
        this.errors.add(new GeneralError(error));
    }

    public void addObjectError(AbstractDomainObject o, String error, Object... args) {
        this.errors.add(new ObjectError(o, FriendlyString.withTypeNames(o, error, args)));
    }

    public void addPropertyError(AbstractDomainObject o, String propertyName, String error, Object... args) {
        this.errors.add(new PropertyError(o, propertyName, FriendlyString.withTypeNames(o, error, args)));
    }

    public List<ValidationError> getErrors() {
        return this.errors;
    }

}
