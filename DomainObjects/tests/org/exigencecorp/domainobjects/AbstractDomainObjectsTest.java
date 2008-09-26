package org.exigencecorp.domainobjects;

import junit.framework.TestCase;

import org.exigencecorp.domainobjects.uow.UoW;

public class AbstractDomainObjectsTest extends TestCase {

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
