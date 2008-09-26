package org.exigencecorp.domainobjects.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.validation.errors.ValidationError;

/** Tracks validation for a UoW. */
public class Validator {

    /** Running list of changed domain objects. */
    private HashSet<DomainObject> queue = new LinkedHashSet<DomainObject>();
    /** Note: Keep dequeue separate so we can avoid re-enqueue's undoing a dequeue. */
    private HashSet<DomainObject> dequeue = new LinkedHashSet<DomainObject>();
    /** All errors we've come across so far in this UoW. */
    private List<ValidationError> errors = new ArrayList<ValidationError>();

    /** @throws ValidationException if validation errors occur */
    public void validate() {
        // If they have outstanding validation errors, don't even try to flush
        if (this.errors.size() > 0) {
            throw new ValidationException("Oustanding validation errors", this.errors);
        }

        // Do updateDerivedValues first
        for (int updatedThrough = 0; updatedThrough < this.queue.size();) {
            DomainObject[] queueSnapshot = this.queue.toArray(new DomainObject[0]);
            for (; updatedThrough < queueSnapshot.length; updatedThrough++) {
                queueSnapshot[updatedThrough].updateDerivedValues();
            }
        }

        // Now go call validation
        for (DomainObject ado : this.queue) {
            this.errors.addAll(ado.validate());
        }

        // If we have errors, don't continue
        if (this.errors.size() > 0) {
            throw new ValidationException("New validation errors", this.errors);
        }
    }

    public void resetQueueAndChangedProperties() {
        for (DomainObject ado : this.queue) {
            ado.clearChangedProperties();
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

    public List<ValidationError> getErrors() {
        return this.errors;
    }

    public int getQueueSize() {
        return this.queue.size();
    }

    public Set<DomainObject> getQueue() {
        return this.queue;
    }

}
