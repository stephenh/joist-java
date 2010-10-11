package joist.util;

import joist.util.ToString;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ToStringTest extends TestCase {

  public void testToString() {
    String s = "asdf";
    Assert.assertEquals("String[4]", ToString.to(s, s.length()));
  }
}
