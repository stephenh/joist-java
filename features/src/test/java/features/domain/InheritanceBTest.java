package features.domain;

import org.junit.Assert;
import org.junit.Test;

public class InheritanceBTest extends AbstractFeaturesTest {

  @Test
  public void testSaveBottom() {
    InheritanceBBottom b = new InheritanceBBottom();
    b.setName("1");
    b.setMiddleName("2");
    b.setBottomName("3");
    this.commitAndReOpen();

    b = InheritanceBBottom.queries.find(b.getId().intValue());
    Assert.assertEquals("1", b.getName());
    Assert.assertEquals("2", b.getMiddleName());
    Assert.assertEquals("3", b.getBottomName());
  }

  @Test
  public void testLoadRootGetsRightSubclass() {
    InheritanceBBottom b = new InheritanceBBottom();
    b.setName("1");
    b.setMiddleName("2");
    b.setBottomName("3");
    this.commitAndReOpen();

    InheritanceBRoot ir = InheritanceBRoot.queries.find(b.getId().intValue());
    Assert.assertTrue(ir instanceof InheritanceBBottom);
  }

  @Test
  public void testIdentityMap() {
    InheritanceBBottom b = new InheritanceBBottom();
    b.setName("1");
    b.setMiddleName("2");
    b.setBottomName("3");
    this.commitAndReOpen();

    InheritanceBRoot ir = InheritanceBRoot.queries.find(b.getId().intValue());
    InheritanceBBottom ib = InheritanceBBottom.queries.find(b.getId().intValue());
    Assert.assertSame(ir, ib);
  }

}
