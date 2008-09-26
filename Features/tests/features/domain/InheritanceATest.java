package features.domain;

import java.util.List;

import junit.framework.Assert;
import features.domain.mappers.InheritanceABaseMapper;
import features.domain.mappers.InheritanceASubOneMapper;
import features.domain.mappers.InheritanceASubTwoMapper;

public class InheritanceATest extends AbstractFeaturesTest {

    public void testSaveInheritanceASubOne() {
        InheritanceASubOne a = new InheritanceASubOne();
        a.setName("name");
        a.setOne("one");
        this.commit();
        Assert.assertEquals(2, a.getId().intValue());

        a = new InheritanceASubOneMapper().find(2);
        Assert.assertEquals("name", a.getName());
        Assert.assertEquals("one", a.getOne());
    }

    public void testInsertInheritanceASubTwo() {
        InheritanceASubTwo b = new InheritanceASubTwo();
        b.setName("name");
        b.setTwo("two");
        this.commit();
        Assert.assertEquals(2, b.getId().intValue());

        b = new InheritanceASubTwoMapper().find(2);
        Assert.assertEquals("name", b.getName());
        Assert.assertEquals("two", b.getTwo());
    }

    public void testUpdateInheritanceASubTwo() {
        InheritanceASubTwo b = new InheritanceASubTwo();
        b.setName("name");
        b.setTwo("two");
        this.commit();
        Assert.assertEquals(2, b.getId().intValue());

        b = new InheritanceASubTwoMapper().find(2);
        b.setName("name2");
        b.setTwo("twotwo");
        this.commit();

        b = new InheritanceASubTwoMapper().find(2);
        Assert.assertEquals("name2", b.getName());
        Assert.assertEquals("twotwo", b.getTwo());
    }

    public void testQueryOnBaseClassReturnsSubClasses() {
        new InheritanceASubOne("namea", "a");
        new InheritanceASubTwo("nameb", "b");
        this.commit();

        List<InheritanceABase> l = new InheritanceABaseMapper().findAll();
        Assert.assertEquals(2, l.size());
        Assert.assertEquals(InheritanceASubOne.class, l.get(0).getClass());
        Assert.assertEquals(InheritanceASubTwo.class, l.get(1).getClass());

        InheritanceASubOne otherA = new InheritanceASubOneMapper().find(2);
        Assert.assertTrue(otherA == l.get(0));
    }

    public void testQueryOnSubClassReturnsOnlyThatSubClass() {
        new InheritanceASubOne("namea", "a");
        new InheritanceASubTwo("nameb", "b");
        this.commit();

        List<InheritanceASubOne> l = new InheritanceASubOneMapper().findAll();
        Assert.assertEquals(1, l.size());
        Assert.assertEquals(InheritanceASubOne.class, l.get(0).getClass());

        InheritanceASubOne otherA = new InheritanceASubOneMapper().find(2);
        Assert.assertTrue(otherA == l.get(0));

        InheritanceABase baseA = new InheritanceABaseMapper().find(2);
        Assert.assertTrue(otherA == baseA);
    }

    public void testQueryOnSubClassByBaseClassAttribute() {
        new InheritanceASubOne("namea", "a");
        new InheritanceASubTwo("nameb", "b");
        this.commit();

        InheritanceASubOne a = new InheritanceASubOneMapper().findByName("namea");
        Assert.assertEquals("a", a.getOne());

        InheritanceASubOne otherA = new InheritanceASubOneMapper().find(2);
        Assert.assertTrue(otherA == a);
    }

}
