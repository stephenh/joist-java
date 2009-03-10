package joist.domain.uow;


import joist.domain.DomainObject;
import joist.domain.orm.IdentityMap;
import joist.domain.orm.Repository;
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

    void open() {
        this.repository.open();
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

}
