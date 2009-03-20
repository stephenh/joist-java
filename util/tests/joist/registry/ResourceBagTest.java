package joist.registry;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ResourceBagTest extends TestCase {

    private ResourceBag bag;

    public void setUp() throws Exception {
        super.setUp();
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

    public static class FakeResource {
        public boolean destroyed = false;
    }

    public static class Factory implements ResourceFactory<FakeResource> {
        public FakeResource create() {
            return new FakeResource();
        }

        public void destroy(FakeResource resource) {
            resource.destroyed = true;
        }
    }

}
