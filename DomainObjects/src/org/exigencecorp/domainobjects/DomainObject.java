package org.exigencecorp.domainobjects;

import java.util.List;

import org.exigencecorp.domainobjects.queries.Alias;
import org.exigencecorp.domainobjects.validation.errors.ValidationError;

public interface DomainObject {

    /** @return the instance/row id */
    Id<? extends DomainObject> getId();

    /** @return the version for the optimistic lock */
    Integer getVersion();

    boolean isNew();

    void clearChangedProperties();

    void updateDerivedValues();

    List<ValidationError> validate();

    Alias<? extends DomainObject> newAlias(String alias);

}
