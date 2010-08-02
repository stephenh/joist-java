package joist.converter;

import junit.framework.Assert;
import junit.framework.TestCase;

public class DefaultConvertersTest extends TestCase {

  private ConverterRegistry r;

  public void setUp() throws Exception {
    super.setUp();
    this.r = ConverterRegistry.newRegistryWithDefaultConverters();
  }

  public void testStringToInt() {
    Assert.assertEquals(new Integer(1), this.r.convert("1", Integer.class));
  }

  public void testNullToInt() {
    // Not sure whether this should fail or return null--for now just document what it does
    try {
      Assert.assertEquals(null, this.r.convert(null, Integer.class));
      Assert.fail();
    } catch (UnsupportedConversionException uce) {
      Assert.assertEquals(Integer.class, uce.getToType());
    }
  }

  public void testObjectToString() {
    Assert.assertEquals("1", this.r.convert(new Integer(1), String.class));
  }

  public void testNullToString() {
    Assert.assertEquals(null, this.r.convert(null, String.class));
  }

  public void testStringToBoolean() {
    Assert.assertEquals(Boolean.TRUE, this.r.convert("true", Boolean.class));
    Assert.assertEquals(Boolean.FALSE, this.r.convert("", Boolean.class));
    Assert.assertEquals(Boolean.FALSE, this.r.convert("false", Boolean.class));
    Assert.assertEquals(Boolean.FALSE, this.r.convert(null, Boolean.class));
  }

}
