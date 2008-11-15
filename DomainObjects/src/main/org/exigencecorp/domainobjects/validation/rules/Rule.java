package org.exigencecorp.domainobjects.validation.rules;

import org.exigencecorp.domainobjects.validation.ValidationErrors;

public interface Rule<T> {

    void validateObject(ValidationErrors errors, T object);

}
