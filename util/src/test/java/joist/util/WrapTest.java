package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class WrapTest {

  @Test
  public void testQuotes() {
    Assert.assertEquals("\"foo\"", Wrap.quotes("foo"));
    Assert.assertEquals("[\"foo\", \"bar\"]", Wrap.quotes("foo", "bar").toString());
  }

  @Test
  public void testBackquotes() {
    Assert.assertEquals("`foo`", Wrap.backquotes("foo"));
    Assert.assertEquals("[`foo`, `bar`]", Wrap.backquotes("foo", "bar").toString());
  }

  @Test
  public void testTicks() {
    Assert.assertEquals("'foo'", Wrap.ticks("foo"));
    Assert.assertEquals("['foo', 'bar']", Wrap.ticks("foo", "bar").toString());
  }

}
