package features.domain;

import junit.framework.Assert;

public class PrimitivesBTest extends AbstractFeaturesTest {

  public void testNullables() {
    PrimitivesB p = new PrimitivesB();
    p.setBool1(null);
    p.setBool2(true);
    p.setInt1(null);
    p.setInt2(2);
    p.setSmall1(null);
    p.setSmall2((short) 2);
    p.setBig1(null);
    p.setBig2(2L);
    this.commitAndReOpen();

    p = this.reload(p);
    Assert.assertNull(p.getBool1());
    Assert.assertTrue(p.getBool2());
    Assert.assertNull(p.getInt1());
    Assert.assertEquals(2, p.getInt2().intValue());
    Assert.assertNull(p.getSmall1());
    Assert.assertEquals(2, p.getSmall2().intValue());
    Assert.assertNull(p.getBig1());
    Assert.assertEquals(2, p.getBig2().intValue());
  }
}
