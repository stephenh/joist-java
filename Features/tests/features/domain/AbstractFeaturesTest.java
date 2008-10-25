package features.domain;

import org.exigencecorp.domainobjects.AbstractDomainObjectsTest;
import org.exigencecorp.jdbc.Jdbc;

import features.Registry;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

    static {
        Registry.getApplicationInstance().start();
    }

    public void setUp() throws Exception {
        super.setUp();
        Jdbc.queryForInt(Registry.getDataSource(), "SELECT flush_test_database()");
    }

}
