package org.exigencecorp.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StringBuilderrTest extends TestCase {

    private StringBuilderr sb;

    public void setUp() {
        this.sb = new StringBuilderr();
    }

    public void testAppendDoesTickToQuoteIfNoArguments() {
        this.sb.append("'asdf'");
        Assert.assertEquals("\"asdf\"", this.sb.toString());
    }

    public void testAppendDoesTickToQuote() {
        this.sb.append("'asdf'", "arg");
        Assert.assertEquals("\"asdf\"", this.sb.toString());
    }

}
