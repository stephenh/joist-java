package features.domain;

import java.util.List;

import junit.framework.Assert;
import features.domain.mappers.ParentBChildFooMapper;

public class ParentBTest extends AbstractFeaturesTest {

    public void testLoadChildren() {
        ParentBParent p = new ParentBParent();
        p.setName("parent");

        ParentBChildFoo f = new ParentBChildFoo();
        f.setName("foo");
        f.setParentBParent(p);

        ParentBChildBar b = new ParentBChildBar();
        b.setName("bar");
        b.setParentBParent(p);
        this.commitAndReOpen();

        List<ParentBChildFoo> foos = new ParentBChildFooMapper().findByParentName("parent");
        Assert.assertEquals("foo", foos.get(0).getName());
    }

}
