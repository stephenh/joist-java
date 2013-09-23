package joist.domain;

import java.util.List;

import joist.domain.validation.errors.ValidationError;

public interface DomainObject {

  /** @return the row id--null if the instance is new */
  Long getId();

  /** the id if specified manually--or when auto-assigned */
  void setId(Long id);

  /** @return the version for the optimistic lock--null if the instance is new */
  Long getVersion();

  boolean isNew();

  boolean isDirty();

  Changed getChanged();

  /** Stub for subclasses to override to update derived values as part of the UoW.flush() process. */
  void updateDerivedValues();

  void clearAssociations();

  List<ValidationError> validate();

  String toTextString();

  boolean wasUpdateDerivedValuesCalled();

  void resetWasUpdateDerivedValuesCalled();

}
