package joist.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class InflectorTest extends TestCase {

    public void testCamelize() {
        Assert.assertEquals("TestOne", Inflector.camelize("test_one"));
    }

    public void testCamelizeSpaces() {
        Assert.assertEquals("TestOne", Inflector.camelize("test one"));
    }

    public void testCamelizeWithTwo() {
        Assert.assertEquals("TestOneTwo", Inflector.camelize("test_one_two"));
    }

    public void testCamelizeWithTwoSpaces() {
        Assert.assertEquals("TestOneTwo", Inflector.camelize("test one two"));
    }

    public void testHumanize() {
        Assert.assertEquals("Test One", Inflector.humanize("testOne"));
        Assert.assertEquals("Test One", Inflector.humanize("TestOne"));
        Assert.assertEquals("Test One O", Inflector.humanize("TestOneO"));
        Assert.assertEquals("Test One 9", Inflector.humanize("TestOne9"));
        Assert.assertEquals("Test URL9", Inflector.humanize("TestURL9"));
        Assert.assertEquals("Test One", Inflector.humanize("test_one"));
        Assert.assertEquals("Test 12", Inflector.humanize("test12"));
    }

    public void testHumanizeWithTwo() {
        Assert.assertEquals("Test One Two", Inflector.humanize("testOneTwo"));
        Assert.assertEquals("Test One Two", Inflector.humanize("TestOneTwo"));
        Assert.assertEquals("Test One Two", Inflector.humanize("test_one_two"));
    }

    public void testUnderscore() {
        Assert.assertEquals("test_one_two", Inflector.underscore("testOneTwo"));
        Assert.assertEquals("test_one_two_1", Inflector.underscore("testOneTwo1"));
        Assert.assertEquals("test_one_two_a", Inflector.underscore("testOneTwoA"));
        Assert.assertEquals("test_one_two", Inflector.underscore("TestOneTwo"));
        Assert.assertEquals("test_one_two", Inflector.underscore("test_one_two"));
    }

    public void testTaxId() {
        Assert.assertEquals("Tax Id", Inflector.humanize("TaxId"));
    }

}
