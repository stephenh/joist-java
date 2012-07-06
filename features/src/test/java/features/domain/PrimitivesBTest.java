package features.domain;

import org.junit.Assert;
import org.junit.Test;

public class PrimitivesBTest extends AbstractFeaturesTest {

  @Test
  public void testNullables() {
    PrimitivesB p = new PrimitivesB();
    Assert.assertFalse(p.getBoolNullableWithDefaultFalse());

    p.setBool1(null);
    p.setBool2(true);
    p.setBoolNullableWithDefaultFalse(null);
    p.setInt1(null);
    p.setInt2(2);
    p.setSmall1(null);
    p.setSmall2((short) 2);
    p.setBig1(null);
    p.setBig2(Long.MAX_VALUE);
    this.commitAndReOpen();

    p = this.reload(p);
    Assert.assertTrue(p.getBool2());
    Assert.assertTrue(p.getBoolWithDefaultTrue());
    Assert.assertNull(p.getInt1());
    Assert.assertEquals(2, p.getInt2().intValue());
    Assert.assertNull(p.getSmall1());
    Assert.assertEquals(2, p.getSmall2().intValue());
    Assert.assertNull(p.getBig1());
    Assert.assertEquals(Long.MAX_VALUE, p.getBig2().longValue());
    Assert.assertNull(p.getBoolNullableWithDefaultFalse());
  }
}
