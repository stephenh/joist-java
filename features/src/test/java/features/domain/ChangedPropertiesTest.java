package features.domain;

import org.junit.Assert;
import org.junit.Test;

public class ChangedPropertiesTest extends AbstractFeaturesTest {

  @Test
  public void testNewPrimitive() {
    Parent p = new Parent();
    p.setName("foo");
    Assert.assertTrue(p.getChanged().contains("name"));
    Assert.assertTrue(p.getChanged().hasName());
    Assert.assertEquals(null, p.getChanged().getOriginal("name"));
    Assert.assertEquals(null, p.getChanged().getOriginalName());
  }

  @Test
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

  @Test
  public void testExistingIdAssignsVersion() {
    Parent p = new Parent();
    p.setId(1l);
    p.setName("foo");
    this.commitAndReOpen();
    Assert.assertEquals(0l, p.getVersion().longValue());
  }

}
