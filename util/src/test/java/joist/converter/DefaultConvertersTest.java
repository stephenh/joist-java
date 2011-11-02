package joist.converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultConvertersTest {

  private ConverterRegistry r;

  @Before
  public void setUp() {
    this.r = ConverterRegistry.newRegistryWithDefaultConverters();
  }

  @Test
  public void testStringToInt() {
    Assert.assertEquals(new Integer(1), this.r.convert("1", Integer.class));
  }

  @Test
  public void testNullToInt() {
    // Not sure whether this should fail or return null--for now just document what it does
    try {
      Assert.assertEquals(null, this.r.convert(null, Integer.class));
      Assert.fail();
    } catch (UnsupportedConversionException uce) {
      Assert.assertEquals(Integer.class, uce.getToType());
    }
  }

  @Test
  public void testObjectToString() {
    Assert.assertEquals("1", this.r.convert(new Integer(1), String.class));
  }

  @Test
  public void testNullToString() {
    Assert.assertEquals(null, this.r.convert(null, String.class));
  }

  @Test
  public void testStringToBoolean() {
    Assert.assertEquals(Boolean.TRUE, this.r.convert("true", Boolean.class));
    Assert.assertEquals(Boolean.FALSE, this.r.convert("", Boolean.class));
    Assert.assertEquals(Boolean.FALSE, this.r.convert("false", Boolean.class));
    Assert.assertEquals(Boolean.FALSE, this.r.convert(null, Boolean.class));
  }

}
