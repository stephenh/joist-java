package features.domain;

import junit.framework.Assert;
import features.domain.queries.Query;

public class ParentTest extends AbstractFeaturesTest {

    public void testLoadChildren() {
        Parent p = new Parent("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commitAndReOpen();

        p = Query.parent.find(2);
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

        Assert.assertTrue(Query.parent.find(2).getChilds().get(0) == Query.child.find(2));
    }

    public void testChildrenArrayAndAfterMapperLoadIsTheSame() {
        Parent p = new Parent("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commitAndReOpen();

        Assert.assertTrue(Query.child.find(2) == Query.parent.find(2).getChilds().get(0));
    }

}
