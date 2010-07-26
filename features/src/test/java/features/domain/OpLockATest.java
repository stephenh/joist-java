package features.domain;

import joist.domain.exceptions.OpLockException;
import joist.jdbc.Jdbc;
import junit.framework.Assert;
import features.Registry;

public class OpLockATest extends AbstractFeaturesTest {

    public void testLoadChildren() {
        Parent p = new Parent("parent");
        this.commitAndReOpen();

        p = this.reload(p);
        p.setName("parent2");

        // Change it first
        Jdbc.update(Registry.getDataSource(), "UPDATE parent SET name = 'changed', version = 1");

        try {
            this.commitAndReOpen();
            Assert.fail();
        } catch (OpLockException ole) {
            Assert.assertEquals("Op lock failed for Parent[1]", ole.getMessage());
        }
    }

}
