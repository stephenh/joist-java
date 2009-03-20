package joist.registry;

import joist.util.TestCounter;
import joist.util.TestCounters;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ResourceBagTest extends TestCase {

    private ResourceBag bag;

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
        this.bag = new ResourceBag();
    }

    public void testGetResourceWithFactoryClassReturnsSameObject() {
        Object one = this.bag.get(Factory.class);
        Object two = this.bag.get(Factory.class);
        Assert.assertSame(one, two);
    }

    public void testDestroyForFactoryClass() {
        FakeResource one = this.bag.get(Factory.class);
        Assert.assertFalse(one.destroyed);
        this.bag.destroyResources();
        Assert.assertTrue(one.destroyed);
    }

    public void testGetLazyResourceWithFactoryClassReturnsSameObject() {
        LazyResource<FakeResource> oneLazy = this.bag.getLazy(Factory.class);
        Assert.assertEquals(0, Factory.created.get());

        Object one = oneLazy.get();
        Assert.assertEquals(1, Factory.created.get());

        Object two = oneLazy.get();
        Assert.assertEquals(1, Factory.created.get());
        Assert.assertSame(one, two);

        // Get another lazy
        Object three = this.bag.getLazy(Factory.class).get();
        Assert.assertEquals(1, Factory.created.get());
        Assert.assertSame(one, three);
    }

    public static class FakeResource {
        public boolean destroyed = false;
    }

    public static class Factory implements ResourceFactory<FakeResource> {
        public static TestCounter created = new TestCounter();

        public FakeResource create() {
            Factory.created.next();
            return new FakeResource();
        }

        public void destroy(FakeResource resource) {
            resource.destroyed = true;
        }
    }

}
