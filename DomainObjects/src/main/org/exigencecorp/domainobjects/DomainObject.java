package org.exigencecorp.domainobjects;

import java.util.List;

import org.exigencecorp.domainobjects.validation.errors.ValidationError;

public interface DomainObject {

    /** @return the instance/row id */
    Integer getId();

    /** @return the version for the optimistic lock */
    Integer getVersion();

    boolean isNew();

    void clearChangedProperties();

    void updateDerivedValues();

    List<ValidationError> validate();

    String toFriendlyTypeName();

    String toFriendlyString();

}
