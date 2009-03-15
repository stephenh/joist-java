package joist.registry;

import joist.registry.ResourceBag;
import joist.registry.ResourceFactory;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ResourceBagTest extends TestCase {

    private ResourceBag bag;

    public void setUp() throws Exception {
        super.setUp();
        this.bag = new ResourceBag();
    }

    public void testGetResourceWithSameFactoryInstanceReturnsSameObject() {
        Factory factory = new Factory();
        Object one = this.bag.getResourceForFactoryInstance(factory);
        Object two = this.bag.getResourceForFactoryInstance(factory);
        Assert.assertSame(one, two);
    }

    public void testGetResourceWithDifferentFactoryInstanceReturnsDifferentObject() {
        Factory factory1 = new Factory();
        Factory factory2 = new Factory();
        Object one = this.bag.getResourceForFactoryInstance(factory1);
        Object two = this.bag.getResourceForFactoryInstance(factory2);
        Assert.assertNotSame(one, two);
    }

    public void testGetResourceWithFactoryClassReturnsSameObject() {
        Object one = this.bag.getResourceForFactoryClass(Factory.class);
        Object two = this.bag.getResourceForFactoryClass(Factory.class);
        Assert.assertSame(one, two);
    }

    public void testGetResourceWithInstanceClassReturnsSameObject() {
        Object one = this.bag.getResourceForInstanceClass(FakeResource.class);
        Object two = this.bag.getResourceForInstanceClass(FakeResource.class);
        Assert.assertSame(one, two);
    }

    public void testDestroyForFactoryClass() {
        FakeResource one = this.bag.getResourceForFactoryClass(Factory.class);
        Assert.assertFalse(one.destroyed);
        this.bag.destroyResources();
        Assert.assertTrue(one.destroyed);
    }

    public void testDestroyForFactoryInstance() {
        Factory factory = new Factory();
        FakeResource one = this.bag.getResourceForFactoryInstance(factory);
        Assert.assertFalse(one.destroyed);
        this.bag.destroyResources();
        Assert.assertTrue(one.destroyed);
    }

    public void testDestroyDoesNotWorkForInstanceClasses() {
        FakeResource one = this.bag.getResourceForInstanceClass(FakeResource.class);
        Assert.assertFalse(one.destroyed);
        this.bag.destroyResources();
        Assert.assertFalse(one.destroyed);
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
