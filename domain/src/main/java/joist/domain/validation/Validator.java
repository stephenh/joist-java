package joist.domain.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joist.domain.DomainObject;
import joist.domain.validation.errors.ValidationError;

/** Tracks validation for a UoW. */
public class Validator {

  /** Running list of changed domain objects. */
  private HashSet<DomainObject> queue = new LinkedHashSet<DomainObject>();
  /** Note: Keep dequeue separate so we can avoid re-enqueue's undoing a dequeue. */
  private HashSet<DomainObject> dequeue = new LinkedHashSet<DomainObject>();

  /** @throws ValidationException if validation errors occur */
  public void validate() {
    // Do updateDerivedValues first
    for (int updatedThrough = 0; updatedThrough < this.queue.size();) {
      DomainObject[] queueSnapshot = this.queue.toArray(new DomainObject[0]);
      for (; updatedThrough < queueSnapshot.length; updatedThrough++) {
        queueSnapshot[updatedThrough].updateDerivedValues();
      }
    }

    // Now go call validation
    List<ValidationError> errors = new ArrayList<ValidationError>();
    for (DomainObject ado : this.queue) {
      errors.addAll(ado.validate());
    }

    // If we have errors, don't continue
    if (errors.size() > 0) {
      throw new ValidationException("New validation errors", errors);
    }
  }

  public void resetQueueAndChangedProperties() {
    for (DomainObject ado : this.queue) {
      ado.getChanged().clear();
    }
    this.queue.clear();
    this.dequeue.clear();
  }

  public void enqueue(DomainObject ado) {
    if (!this.dequeue.contains(ado)) {
      this.queue.add(ado);
    }
  }

  public void dequeue(DomainObject ado) {
    this.queue.remove(ado);
    this.dequeue.add(ado);
  }

  public Set<DomainObject> getQueue() {
    return this.queue;
  }

}
