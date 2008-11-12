package features.domain;

import junit.framework.Assert;
import features.domain.queries.Query;

public class InheritanceBTest extends AbstractFeaturesTest {

    public void testSaveBottom() {
        InheritanceBBottom b = new InheritanceBBottom();
        b.setName("1");
        b.setMiddleName("2");
        b.setBottomName("3");
        this.commitAndReOpen();

        b = Query.inheritanceBBottom.find(b.getId().intValue());
        Assert.assertEquals("1", b.getName());
        Assert.assertEquals("2", b.getMiddleName());
        Assert.assertEquals("3", b.getBottomName());
    }

    public void testLoadRootGetsRightSubclass() {
        InheritanceBBottom b = new InheritanceBBottom();
        b.setName("1");
        b.setMiddleName("2");
        b.setBottomName("3");
        this.commitAndReOpen();

        InheritanceBRoot ir = Query.inheritanceBRoot.find(b.getId().intValue());
        Assert.assertTrue(ir instanceof InheritanceBBottom);
    }

    public void testIdentityMap() {
        InheritanceBBottom b = new InheritanceBBottom();
        b.setName("1");
        b.setMiddleName("2");
        b.setBottomName("3");
        this.commitAndReOpen();

        InheritanceBRoot ir = Query.inheritanceBRoot.find(b.getId().intValue());
        InheritanceBBottom ib = Query.inheritanceBBottom.find(b.getId().intValue());
        Assert.assertSame(ir, ib);
    }

}
