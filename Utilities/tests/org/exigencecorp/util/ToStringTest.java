package org.exigencecorp.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ToStringTest extends TestCase {

    public void testTicksToQuotesGetsSingleTicks() {
        Assert.assertEquals("i said \"hi\" then", ToString.tickToQuote("i said 'hi' then"));
    }

    public void testTicksToQuotesMissesDoubleTicks() {
        Assert.assertEquals("i said 'hi' then", ToString.tickToQuote("i said ''hi'' then"));
    }

}
