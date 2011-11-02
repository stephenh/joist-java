package joist.converter;

import java.util.Calendar;

import joist.util.TestCounters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

  @Before
  public void setUp() {
    TestCounters.resetAll();
  }

  @Test
  public void testIdentityWorks() {
    ConverterRegistry r = new ConverterRegistry();
    Integer i = new Integer(1);
    Assert.assertSame(i, r.convert(i, Integer.class));

    // Cached
    Assert.assertSame(i, r.convert(i, Integer.class));
    Assert.assertEquals(1, ConverterRegistry.probes.get());
  }

  @Test
  public void testSubAsBaseWorksWithoutConversion() {
    ConverterRegistry r = new ConverterRegistry();
    Sub s = new Sub();
    Assert.assertSame(s, r.convert(s, Base.class));
    Assert.assertEquals(1, ConverterRegistry.probes.get());

    // Cached
    Assert.assertSame(s, r.convert(s, Base.class));
    Assert.assertEquals(1, ConverterRegistry.probes.get());
  }

  @Test
  public void testBaseCannotGoToSub() {
    ConverterRegistry r = new ConverterRegistry();
    Base b = new Base();
    try {
      r.convert(b, Sub.class);
      Assert.fail();
    } catch (UnsupportedConversionException uce) {
      Assert.assertEquals(Sub.class, uce.getToType());
    }
  }

  @Test
  public void testFailsIfUnsupported() {
    ConverterRegistry r = new ConverterRegistry();
    try {
      r.convert(1, Calendar.class);
      Assert.fail();
    } catch (UnsupportedConversionException uce) {
      Assert.assertEquals(Calendar.class, uce.getToType());
    }
  }

  public static class Base {
  }

  public static class Sub extends Base {
  }
}
