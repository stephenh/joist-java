package org.exigencecorp.domainobjects.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exigencecorp.domainobjects.validation.errors.ValidationError;

/** Throw when someone calls flush and validation errors occur. */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1;
    private List<ValidationError> errors = null;

    public ValidationException(String message, List<ValidationError> errors) {
        super(message + " - " + StringUtils.join(errors, ", "));
        this.errors = errors;
    }

    public int getNumberOfErrors() {
        return this.errors.size();
    }

    public List<ValidationError> getValidationErrors() {
        return this.errors;
    }

    public List<String> getValidationErrorMessages() {
        List<String> messages = new ArrayList<String>();
        for (ValidationError ve : this.getValidationErrors()) {
            messages.add(ve.getMessage());
        }
        return messages;
    }

}
