package features.domain;

import junit.framework.Assert;

public class ChangedPropertiesTest extends AbstractFeaturesTest {

  public void testNewPrimitive() {
    Parent p = new Parent();
    p.setName("foo");
    Assert.assertTrue(p.getChanged().contains("name"));
    Assert.assertTrue(p.getChanged().hasName());
    Assert.assertEquals(null, p.getChanged().getOriginal("name"));
    Assert.assertEquals(null, p.getChanged().getOriginalName());
  }

  public void testExistingPrimitive() {
    Parent p = new Parent();
    p.setName("foo");
    this.commitAndReOpen();

    p = this.reload(p);
    p.setName("foo2");
    p.setName("foo3");
    Assert.assertTrue(p.getChanged().contains("name"));
    Assert.assertTrue(p.getChanged().hasName());
    Assert.assertEquals("foo", p.getChanged().getOriginal("name"));
    Assert.assertEquals("foo", p.getChanged().getOriginalName());
  }

}
