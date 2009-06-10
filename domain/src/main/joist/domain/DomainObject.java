package joist.domain;

import java.util.List;

import joist.domain.validation.errors.ValidationError;

public interface DomainObject {

    /** @return the row id--null if the instance is new */
    Integer getId();

    /** the id if specified manually--or when auto-assigned */
    void setId(Integer id);

    /** @return the version for the optimistic lock--null if the instance is new */
    Integer getVersion();

    boolean isNew();

    boolean isDirty();

    Changed getChanged();

    /** Stub for subclasses to override to update derived values as part of the UoW.flush() process. */
    void updateDerivedValues();

    void clearAssociations();

    List<ValidationError> validate();

    String toTextString();

}
