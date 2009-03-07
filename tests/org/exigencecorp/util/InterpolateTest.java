package org.exigencecorp.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class InterpolateTest extends TestCase {

    public void testAlone() {
        Assert.assertEquals("foo", Interpolate.string("{}", "foo"));
    }

    public void testBegin() {
        Assert.assertEquals("foo1", Interpolate.string("{}1", "foo"));
    }

    public void testEnd() {
        Assert.assertEquals("1foo", Interpolate.string("1{}", "foo"));
    }

    public void testMiddle() {
        Assert.assertEquals("1foo2", Interpolate.string("1{}2", "foo"));
    }

    public void testAll() {
        Assert.assertEquals("1f2f3", Interpolate.string("{}f{}f{}", "1", "2", "3"));
    }
}
