package joist.domain.uow;

import java.util.concurrent.atomic.AtomicReference;

import joist.domain.DomainObject;
import joist.domain.orm.IdentityMap;
import joist.domain.orm.Repository;
import joist.domain.orm.Updater;
import joist.domain.validation.Validator;

/** Coordinates validation, object identity, and storing/retrieving domain objects.
 *
 * All of these methods are package private so that the UoW facade class has to
 * be used. I'm not entirely sure this is a good idea.
 */
public class UnitOfWork {

    private final Validator validator = new Validator();
    private final IdentityMap identityMap = new IdentityMap();
    private final Repository repository = new Repository();
    private final AtomicReference<Updater> updater = new AtomicReference<Updater>();

    void open(final Updater updater) {
        this.updater.set(updater);
        this.repository.open(updater);
    }

    void close() {
        this.repository.close();
    }

    void flush() {
        this.validator.validate();
        this.repository.store(this.validator.getQueue());
        this.validator.resetQueueAndChangedProperties();
    }

    void delete(DomainObject instance) {
        this.validator.dequeue(instance);
        this.repository.delete(instance);
    }

    void commit() {
        this.flush();
        this.repository.commit();
    }

    void rollback() {
        this.repository.rollback();
    }

    IdentityMap getIdentityMap() {
        return this.identityMap;
    }

    Validator getValidator() {
        return this.validator;
    }

    Repository getRepository() {
        return this.repository;
    }

    void setUpdater(final Updater updater) {
        this.updater.set(updater);
    }

    Updater getUpdater() {
        return this.updater.get();
    }
}
