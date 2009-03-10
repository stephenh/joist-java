package joist.domain;

import joist.domain.uow.UoW;
import junit.framework.TestCase;

import org.exigencecorp.util.TestCounters;

public abstract class AbstractDomainObjectsTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
        UoW.open();
    }

    public void tearDown() throws Exception {
        UoW.close();
        super.tearDown();
    }

    protected void commitAndReOpen() {
        UoW.commit();
        UoW.close();
        UoW.open();
    }

    protected void rollback() {
        UoW.rollback();
    }

    protected void flush() {
        UoW.flush();
    }

    protected <T extends DomainObject> T reload(T instance) {
        return (T) UoW.load(instance.getClass(), instance.getId());
    }

}
