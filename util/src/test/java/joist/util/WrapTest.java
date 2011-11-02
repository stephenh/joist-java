package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class WrapTest {

  @Test
  public void testQuotes() {
    Assert.assertEquals("\"foo\"", Wrap.quotes("foo"));
  }

}
