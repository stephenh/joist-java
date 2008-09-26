package org.exigencecorp.util;

import org.exigencecorp.util.Inflector;

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
        Assert.assertEquals("test_one_two", Inflector.underscore("TestOneTwo"));
        Assert.assertEquals("test_one_two", Inflector.underscore("test_one_two"));
    }

    public void testTaxId() {
        Assert.assertEquals("Tax Id", Inflector.humanize("TaxId"));
    }

}
