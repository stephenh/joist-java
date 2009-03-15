package org.exigencecorp.util;

import org.exigencecorp.util.Wrap;

import junit.framework.Assert;
import junit.framework.TestCase;

public class WrapTest extends TestCase {

    public void testQuotes() {
        Assert.assertEquals("\"foo\"", Wrap.quotes("foo"));
    }

}
