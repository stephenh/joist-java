package features.domain;

import org.exigencecorp.domainobjects.AbstractDomainObjectsTest;
import org.exigencecorp.domainobjects.util.FlushDatabase;

import features.Registry;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

    private static final FlushDatabase flushDatabase;
    static {
        Registry.getApplicationInstance().start();
        flushDatabase = new FlushDatabase(Registry.getDataSource());
    }

    public void setUp() throws Exception {
        super.setUp();
        AbstractFeaturesTest.flushDatabase.flush();
    }

}
