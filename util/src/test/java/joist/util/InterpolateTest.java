package joist.util;

import joist.converter.AbstractOneWayConverter;
import joist.converter.ConverterRegistry;

import org.junit.Assert;
import org.junit.Test;

public class InterpolateTest {

  @Test
  public void testAlone() {
    Assert.assertEquals("foo", Interpolate.string("{}", "foo"));
  }

  @Test
  public void testBegin() {
    Assert.assertEquals("foo1", Interpolate.string("{}1", "foo"));
  }

  @Test
  public void testEnd() {
    Assert.assertEquals("1foo", Interpolate.string("1{}", "foo"));
  }

  @Test
  public void testMiddle() {
    Assert.assertEquals("1foo2", Interpolate.string("1{}2", "foo"));
  }

  @Test
  public void testAll() {
    Assert.assertEquals("1f2f3", Interpolate.string("{}f{}f{}", "1", "2", "3"));
  }

  @Test
  public void testNull() {
    Assert.assertEquals("null", Interpolate.string("{}", new Object[] { null }));
  }

  @Test
  public void testWithConverter() {
    ConverterRegistry r = new ConverterRegistry();
    r.addConverter(new AbstractOneWayConverter<Integer, String>() {
      public String convertOneToTwo(Integer value, Class<? extends String> toType) {
        return "blah";
      }
    });
    Assert.assertEquals("blah", Interpolate.string("{}", r, new Integer(1)));
  }
}
