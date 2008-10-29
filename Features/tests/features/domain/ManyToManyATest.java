package features.domain;

import junit.framework.Assert;
import features.domain.mappers.ManyToManyAFooMapper;

public class ManyToManyATest extends AbstractFeaturesTest {

    public void testTwo() {
        ManyToManyAFoo foo = new ManyToManyAFoo();
        foo.setName("foo");
        ManyToManyABar bar = new ManyToManyABar();
        bar.setName("bar");

        ManyToManyAFooToBar join = new ManyToManyAFooToBar();
        join.setManyToManyAFoo(foo);
        join.setManyToManyABar(bar);
        this.commitAndReOpen();

        foo = new ManyToManyAFooMapper().find(foo.getId());
        Assert.assertEquals("bar", foo.getManyToManyAFooToBars().get(0).getManyToManyABar().getName());
    }

}
