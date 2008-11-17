package features.domain;

import junit.framework.Assert;

public class OneToOneATest extends AbstractFeaturesTest {

    public void testOneSide() {
        OneToOneABar bar = new OneToOneABar("bar");
        OneToOneAFoo foo = new OneToOneAFoo("foo");
        bar.setOneToOneAFoo(foo);
        this.commitAndReOpen();
        bar = this.reload(bar);
        Assert.assertEquals("foo", bar.getOneToOneAFoo().getName());
    }

    public void testManySide() {
        OneToOneABar bar = new OneToOneABar("bar");
        OneToOneAFoo foo = new OneToOneAFoo("foo");
        foo.setOneToOneABar(bar);
        this.commitAndReOpen();
        foo = this.reload(foo);
        Assert.assertEquals("bar", foo.getOneToOneABar().getName());
    }

}
