package joist.registry;

import joist.util.TestCounter;
import joist.util.TestCounters;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ResourceRefsTest extends TestCase {

  public void setUp() throws Exception {
    super.setUp();
    TestCounters.resetAll();
  }

  public void testSameSingletonRefOnlyHasOneInstance() {
    RegistryWithSingleton r = new RegistryWithSingleton();
    Assert.assertSame(r.a.get(), r.a.get());
    Assert.assertSame(r.b.get(), r.b.get());
    Assert.assertNotSame(r.a.get(), r.b.get());
    Assert.assertEquals(2, MockSingleton.instances.get());
  }

  public void testLazyLoadSingletonRef() {
    RegistryWithSingleton r = new RegistryWithSingleton();
    Assert.assertEquals(1, MockSingleton.instances.get()); // a but not b
    Assert.assertSame(r.a.get(), r.a.get());
    Assert.assertEquals(2, MockSingleton.instances.get());
  }

  public void testStop() {
    RegistryWithSingleton r = new RegistryWithSingleton();
    Assert.assertSame(r.c.get(), r.c.get());
    r.refs.stop();
    Assert.assertEquals(1, MockSingletonFactory.destroyed.get());
  }

  public void testStopDoesNotStartUnstartedResources() {
    RegistryWithSingleton r = new RegistryWithSingleton();
    r.refs.stop();
    Assert.assertEquals(0, MockSingletonFactory.destroyed.get());
  }

  private static class RegistryWithSingleton {
    private final ResourceRefs refs = new ResourceRefs();
    private final ResourceRef<MockSingleton> a = this.refs.newRef(MockSingleton.class).make();
    private final ResourceRef<MockSingleton> b = this.refs.newRef(MockSingleton.class).loadOnStart(true).make();
    private final ResourceRef<MockSingleton> c = this.refs.newRef(MockSingleton.class).factory(new MockSingletonFactory()).make();

    private RegistryWithSingleton() {
      this.refs.start();
    }
  }

  public static class MockSingleton {
    private static final TestCounter instances = new TestCounter();

    public MockSingleton() {
      MockSingleton.instances.tick();
    }
  }

  public static class MockSingletonFactory implements ResourceFactory<MockSingleton> {
    private static final TestCounter destroyed = new TestCounter();

    public MockSingleton create() {
      return new MockSingleton();
    }

    public void destroy(MockSingleton resource) {
      MockSingletonFactory.destroyed.tick();
    }
  }

}
