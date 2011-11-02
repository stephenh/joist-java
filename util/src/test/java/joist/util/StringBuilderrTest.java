package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class StringBuilderrTest {

  private StringBuilderr sb = new StringBuilderr();

  @Test
  public void testAppendDoesNotTickToQuoteIfNoArguments() {
    this.sb.append("'asdf'");
    Assert.assertEquals("'asdf'", this.sb.toString());
  }

  @Test
  public void testAppendDoesNotTickToQuote() {
    this.sb.append("'asdf' {}", "arg");
    Assert.assertEquals("'asdf' arg", this.sb.toString());
  }

}
