package org.exigencecorp.domainobjects.uow;

import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.orm.ObjectCache;
import org.exigencecorp.domainobjects.orm.repos.Repository;
import org.exigencecorp.domainobjects.orm.repos.sql.JdbcRepository;
import org.exigencecorp.domainobjects.validation.Validator;
import org.exigencecorp.util.Log;

/** Coordinates validation, object identity, and storing/retrieving domain objects. */
public class UnitOfWork {

    private final Validator validator = new Validator();
    private final ObjectCache objectCache = new ObjectCache();
    private final Repository repository = new JdbcRepository();

    public void open() {
        this.repository.open();
    }

    public void close() {
        this.repository.close();
    }

    public void flush() {
        for (DomainObject instance : this.validator.getQueue()) {
            if (instance.getId() == null) {
                this.repository.assignId(instance);
            }
        }
        for (DomainObject instance : this.validator.getQueue()) {
            Log.debug("Saving {}", instance);
            this.repository.store(instance);
        }
        this.validator.resetQueueAndChangedProperties();
    }

    public void delete(DomainObject instance) {
        this.repository.delete(instance);
    }

    public void commit() {
        this.flush();
        this.repository.commit();
    }

    public void rollback() {
        this.repository.rollback();
    }

    public ObjectCache getObjectCache() {
        return this.objectCache;
    }

    public Validator getValidator() {
        return this.validator;
    }

    public Repository getRepository() {
        return this.repository;
    }

}
