package joist.util;

import joist.converter.AbstractOneWayConverter;
import joist.converter.ConverterRegistry;
import joist.util.Interpolate;
import junit.framework.Assert;
import junit.framework.TestCase;

public class InterpolateTest extends TestCase {

  public void testAlone() {
    Assert.assertEquals("foo", Interpolate.string("{}", "foo"));
  }

  public void testBegin() {
    Assert.assertEquals("foo1", Interpolate.string("{}1", "foo"));
  }

  public void testEnd() {
    Assert.assertEquals("1foo", Interpolate.string("1{}", "foo"));
  }

  public void testMiddle() {
    Assert.assertEquals("1foo2", Interpolate.string("1{}2", "foo"));
  }

  public void testAll() {
    Assert.assertEquals("1f2f3", Interpolate.string("{}f{}f{}", "1", "2", "3"));
  }

  public void testNull() {
    Assert.assertEquals("null", Interpolate.string("{}", new Object[] { null }));
  }

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
