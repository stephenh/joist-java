package @projectName@;

import joist.domain.AbstractDomainObjectsTest;
import joist.domain.util.Freeze;
import joist.jdbc.Jdbc;

public abstract class Abstract@ProjectName@IntegrationTest extends AbstractDomainObjectsTest {

    private static boolean started = false;

    public void setUp() throws Exception {
        if (!started) {
            Registry.start();
            started = true;
        }
        super.setUp();
        Freeze.at(2008, 1, 1);
        Jdbc.queryForInt(Registry.getDataSource(), "SELECT flush_test_database()");
    }

}
