package features.domain;

import junit.framework.Assert;

public class ParentTest extends AbstractFeaturesTest {

    public void testLoadChildren() {
        Parent p = new Parent("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commitAndReOpen();

        p = Parent.queries.find(2);
        Assert.assertEquals(2, p.getChilds().size());
        Assert.assertEquals("child1", p.getChilds().get(0).getName());
        Assert.assertEquals("child2", p.getChilds().get(1).getName());

        Assert.assertTrue(p.getChilds() == p.getChilds());
    }

    public void testChildrenArrayAndThenMapperLoadIsTheSame() {
        Parent p = new Parent("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commitAndReOpen();

        Assert.assertTrue(Parent.queries.find(2).getChilds().get(0) == Child.queries.find(2));
    }

    public void testChildrenArrayAndAfterMapperLoadIsTheSame() {
        Parent p = new Parent("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commitAndReOpen();

        Assert.assertTrue(Child.queries.find(2) == Parent.queries.find(2).getChilds().get(0));
    }

}
