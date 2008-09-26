package features.domain;

import org.exigencecorp.domainobjects.AbstractDomainObjectsTest;
import org.exigencecorp.domainobjects.uow.UoW;
import org.exigencecorp.domainobjects.util.FlushDatabase;

import features.Registry;

public abstract class AbstractFeaturesTest extends AbstractDomainObjectsTest {

    public void setUp() throws Exception {
        super.setUp();

        Registry.getApplicationInstance().start();
        new FlushDatabase(Registry.getDataSource()).flush();

        UoW.open();
    }

}
