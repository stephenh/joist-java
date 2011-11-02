package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class ToStringTest {

  @Test
  public void testToString() {
    String s = "asdf";
    Assert.assertEquals("String[4]", ToString.to(s, s.length()));
  }
}
