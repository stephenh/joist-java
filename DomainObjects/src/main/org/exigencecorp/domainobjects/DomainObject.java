package org.exigencecorp.domainobjects;

import java.util.List;

import org.exigencecorp.domainobjects.validation.errors.ValidationError;

public interface DomainObject {

    /** @return the row id--null if the instance is new */
    Integer getId();

    /** @return the version for the optimistic lock--null if the instance is new */
    Integer getVersion();

    boolean isNew();

    boolean isDirty();

    void clearChangedProperties();

    void updateDerivedValues();

    List<ValidationError> validate();

    String toFriendlyTypeName();

    String toFriendlyString();

}
