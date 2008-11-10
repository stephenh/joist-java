package features.domain;

import org.exigencecorp.domainobjects.AbstractDomainObjectsTest;
import org.exigencecorp.domainobjects.DomainObject;
import org.exigencecorp.domainobjects.uow.UoW;
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

    public <T extends DomainObject> T reload(T instance) {
        return (T) UoW.getCurrent().getRepository().load(instance.getClass(), instance.getId().intValue());
    }

}
