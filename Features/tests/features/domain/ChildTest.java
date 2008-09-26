package features.domain;

import java.util.List;

import junit.framework.Assert;
import features.domain.Child;
import features.domain.Parent;
import features.domain.mappers.ChildMapper;
import features.domain.mappers.ParentMapper;

public class ChildTest extends AbstractFeaturesTest {

    public void testSaveAndReloadChildInSeparateUoWThanParent() {
        Parent p = new Parent();
        p.setName("parent");
        this.commit();

        Child c = new Child();
        c.setName("child");
        c.setParent(new ParentMapper().find(2));
        this.commit();

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
        this.commit();

        c = new ChildMapper().find(2);
        p = new ParentMapper().find(2);
        Assert.assertTrue(p == c.getParent());
    }

    public void testFindByParentName() {
        Parent p = new Parent();
        p.setName("parent");
        new Child(p, "child1");
        new Child(p, "child2");
        this.commit();

        List<Child> children = new ChildMapper().findForParentOfName("parent");
        Assert.assertEquals(2, children.size());
        Assert.assertEquals("child1", children.get(0).getName());
        Assert.assertEquals("child2", children.get(1).getName());
    }

}
