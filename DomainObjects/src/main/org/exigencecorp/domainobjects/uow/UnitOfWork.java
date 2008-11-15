package org.exigencecorp.domainobjects.uow;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.orm.IdentityMap;
import org.exigencecorp.domainobjects.orm.Repository;
import org.exigencecorp.domainobjects.validation.Validator;

/** Coordinates validation, object identity, and storing/retrieving domain objects. */
public class UnitOfWork {

    private final Validator validator = new Validator();
    private final IdentityMap identityMap = new IdentityMap();
    private final Repository repository = new Repository();

    public void open() {
        this.repository.open();
    }

    public void close() {
        this.repository.close();
    }

    public void flush() {
        this.validator.validate();
        this.repository.store(this.validator.getQueue());
        this.validator.resetQueueAndChangedProperties();
    }

    public void delete(DomainObject instance) {
        this.validator.dequeue(instance);
        this.repository.delete(instance);
    }

    public void commit() {
        this.flush();
        this.repository.commit();
    }

    public void rollback() {
        this.repository.rollback();
    }

    public IdentityMap getIdentityMap() {
        return this.identityMap;
    }

    public Validator getValidator() {
        return this.validator;
    }

    public Repository getRepository() {
        return this.repository;
    }

}
