package joist.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class WrapTest extends TestCase {

    public void testQuotes() {
        Assert.assertEquals("\"foo2\"", Wrap.quotes("foo"));
    }

}
