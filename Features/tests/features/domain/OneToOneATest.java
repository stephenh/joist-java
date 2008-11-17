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

    public void testOneSideTwice() {
        OneToOneAFoo foo = new OneToOneAFoo("foo");
        OneToOneABar bar1 = new OneToOneABar("bar");
        bar1.setOneToOneAFoo(foo);
        OneToOneABar bar2 = new OneToOneABar("bar");
        bar2.setOneToOneAFoo(foo);
        Assert.assertNull(bar1.getOneToOneAFoo());
    }

    public void testManySide() {
        OneToOneABar bar = new OneToOneABar("bar");
        OneToOneAFoo foo = new OneToOneAFoo("foo");
        foo.setOneToOneABar(bar);
        this.commitAndReOpen();
        foo = this.reload(foo);
        Assert.assertEquals("bar", foo.getOneToOneABar().getName());
    }

    public void testUniqueOneNameToo() {
        OneToOneBFoo foo = new OneToOneBFoo("foo");
        OneToOneBBar bar1 = new OneToOneBBar("bar1");
        OneToOneBBar bar2 = new OneToOneBBar("bar2");
        bar1.setOneToOneBFoo(foo);
        bar2.setOneToOneBFoo(foo);
        this.commitAndReOpen();

        bar1 = this.reload(bar1);
        Assert.assertEquals("foo", bar1.getOneToOneBFoo().getName());
        bar2 = this.reload(bar2);
        Assert.assertEquals("foo", bar2.getOneToOneBFoo().getName());
    }

}
