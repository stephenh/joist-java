package features.domain;

import junit.framework.Assert;
import features.domain.mappers.ParentMapper;

public class ParentTest extends AbstractFeaturesTest {

    public void testSaveAndReloadChildInSeparateUoWThanParent() {
        Parent p = new Parent("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commitAndReOpen();

        p = new ParentMapper().find(2);
        Assert.assertEquals(2, p.getChilds().size());
        Assert.assertEquals("child1", p.getChilds().get(0).getName());
        Assert.assertEquals("child2", p.getChilds().get(1).getName());

        Assert.assertTrue(p.getChilds() == p.getChilds());
    }

}
