package org.exigencecorp.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class StringBuilderrTest extends TestCase {

    private StringBuilderr sb;

    public void setUp() {
        this.sb = new StringBuilderr();
    }

    public void testAppendDoesNotTickToQuoteIfNoArguments() {
        this.sb.append("'asdf'");
        Assert.assertEquals("'asdf'", this.sb.toString());
    }

    public void testAppendDoesNotTickToQuote() {
        this.sb.append("'asdf' {}", "arg");
        Assert.assertEquals("'asdf' arg", this.sb.toString());
    }

}
