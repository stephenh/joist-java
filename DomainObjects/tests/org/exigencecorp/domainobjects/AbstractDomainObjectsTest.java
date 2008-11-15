package org.exigencecorp.domainobjects;

import junit.framework.TestCase;

import org.exigencecorp.domainobjects.uow.UoW;

public abstract class AbstractDomainObjectsTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
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

}
