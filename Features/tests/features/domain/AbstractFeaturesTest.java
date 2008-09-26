package features.domain;

import junit.framework.TestCase;

import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.util.FlushDatabase;

import features.Registry;

public abstract class AbstractFeaturesTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();

        Registry.getApplicationInstance().start();
        new FlushDatabase(Registry.getDataSource()).flush();

        UoW.open();
    }

    public void tearDown() throws Exception {
        UoW.close();
        super.tearDown();
    }

    protected void commit() {
        UoW.commit();
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
