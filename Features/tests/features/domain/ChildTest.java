package features.domain;

import java.util.List;

import junit.framework.Assert;
import features.domain.mappers.ChildMapper;
import features.domain.mappers.ParentMapper;

public class ChildTest extends AbstractFeaturesTest {

    public void testSaveAndReloadChildInSeparateUoWThanParent() {
        Parent p = new Parent();
        p.setName("parent");
        this.commitAndReOpen();

        Child c = new Child();
        c.setName("child");
        c.setParent(new ParentMapper().find(2));
        this.commitAndReOpen();

        c = new ChildMapper().find(2);
        p = new ParentMapper().find(2);
        Assert.assertTrue(p == c.getParent());
    }

    public void testSaveAndReloadChildInSameUoWAsParent() {
        Parent p = new Parent();
        p.setName("parent");

        Child c = new Child();
        c.setName("child");
        c.setParent(p);
        this.commitAndReOpen();

        c = new ChildMapper().find(2);
        p = new ParentMapper().find(2);
        Assert.assertTrue(p == c.getParent());
    }

    public void testFindByParentName() {
        Parent p = new Parent();
        p.setName("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commitAndReOpen();

        List<Child> children = new ChildMapper().findForParentOfName("parent");
        Assert.assertEquals(2, children.size());
        Assert.assertEquals("child1", children.get(0).getName());
        Assert.assertEquals("child2", children.get(1).getName());
    }

    public void testChangeOfParentIsSaved() {
        Parent p1 = new Parent("p1");
        new Child(p1, "child");
        this.commitAndReOpen();

        Child c = new ChildMapper().find(2);
        c.setParent(new Parent("p2"));
        Assert.assertEquals("parent", c.getChangedProperties().get(0));
        this.commitAndReOpen();

        c = new ChildMapper().find(2);
        Assert.assertEquals("p2", c.getParent().getName());
    }

    public void testPercolationFromChildToParent() {
        Parent p = new Parent("p");
        Child c = new Child();
        c.setParent(p);
        Assert.assertEquals(1, p.getChilds().size());
        Assert.assertTrue(c.getChangedProperties().contains("parent"));
        Assert.assertTrue(p.getChangedProperties().contains("childs"));

        c.setParent(null);
        Assert.assertEquals(0, p.getChilds().size());
        Assert.assertTrue(c.getChangedProperties().contains("parent"));
        Assert.assertTrue(p.getChangedProperties().contains("childs"));
    }

    public void testPercolationFromParentToChild() {
        Parent p = new Parent("p");
        Child c = new Child();
        p.addChild(c);
        Assert.assertEquals(p, c.getParent());
        Assert.assertTrue(c.getChangedProperties().contains("parent"));
        Assert.assertTrue(p.getChangedProperties().contains("childs"));

        p.removeChild(c);
        Assert.assertEquals(null, c.getParent());
        Assert.assertTrue(c.getChangedProperties().contains("parent"));
        Assert.assertTrue(p.getChangedProperties().contains("childs"));
    }

}
