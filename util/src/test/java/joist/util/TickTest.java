package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class TickTest {

  @Test
  public void testTicksToQuotesGetsSingleTicks() {
    Assert.assertEquals("i said \"hi\" then", Tick.toQuote("i said 'hi' then"));
  }

  @Test
  public void testTicksToQuotesMissesDoubleTicks() {
    Assert.assertEquals("i said 'hi' then", Tick.toQuote("i said ''hi'' then"));
  }

}
