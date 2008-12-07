package org.exigencecorp.domainobjects.validation.rules;

import org.exigencecorp.domainobjects.validation.ValidationErrors;

public interface Rule<T> {

    void validate(ValidationErrors errors, T object);

}
