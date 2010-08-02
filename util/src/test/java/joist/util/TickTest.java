package joist.util;

import joist.util.Tick;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TickTest extends TestCase {

  public void testTicksToQuotesGetsSingleTicks() {
    Assert.assertEquals("i said \"hi\" then", Tick.toQuote("i said 'hi' then"));
  }

  public void testTicksToQuotesMissesDoubleTicks() {
    Assert.assertEquals("i said 'hi' then", Tick.toQuote("i said ''hi'' then"));
  }

}
