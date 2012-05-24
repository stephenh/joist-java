package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class InflectorTest {

  @Test
  public void testCamelize() {
    Assert.assertEquals("TestOne", Inflector.camelize("test_one"));
  }

  @Test
  public void testCamelizeSpaces() {
    Assert.assertEquals("TestOne", Inflector.camelize("test one"));
  }

  @Test
  public void testCamelizeWithTwo() {
    Assert.assertEquals("TestOneTwo", Inflector.camelize("test_one_two"));
  }

  @Test
  public void testCamelizeWithTwoSpaces() {
    Assert.assertEquals("TestOneTwo", Inflector.camelize("test one two"));
  }

  @Test
  public void testHumanize() {
    Assert.assertEquals("Test One", Inflector.humanize("testOne"));
    Assert.assertEquals("Test One", Inflector.humanize("TestOne"));
    Assert.assertEquals("Test One O", Inflector.humanize("TestOneO"));
    Assert.assertEquals("Test One 19", Inflector.humanize("TestOne19"));
    Assert.assertEquals("Test Url 91 Foo", Inflector.humanize("TestURL91Foo"));
    Assert.assertEquals("Test One", Inflector.humanize("test_one"));
    Assert.assertEquals("Test One", Inflector.humanize("TEST_ONE"));
    Assert.assertEquals("Test 12", Inflector.humanize("test12"));
  }

  @Test
  public void testHumanizeWithTwo() {
    Assert.assertEquals("Test One Two", Inflector.humanize("testOneTwo"));
    Assert.assertEquals("Test One Two", Inflector.humanize("TestOneTwo"));
    Assert.assertEquals("Test One Two", Inflector.humanize("test_one_two"));
    Assert.assertEquals("Test One Two", Inflector.humanize("TEST_ONE_TWO"));
    Assert.assertEquals("Test A B", Inflector.humanize("test_a_b"));
    Assert.assertEquals("Test A B", Inflector.humanize("TEST_A_B"));
  }

  @Test
  public void testUnderscore() {
    Assert.assertEquals("test_one_two", Inflector.underscore("testOneTwo"));
    Assert.assertEquals("test_one_two_1", Inflector.underscore("testOneTwo1"));
    Assert.assertEquals("test_one_two_a", Inflector.underscore("testOneTwoA"));
    Assert.assertEquals("test_one_two", Inflector.underscore("TestOneTwo"));
    Assert.assertEquals("test_one_two", Inflector.underscore("test_one_two"));
    Assert.assertEquals("test_one_two", Inflector.underscore("TEST_ONE_TWO"));
  }

  @Test
  public void testTaxId() {
    Assert.assertEquals("Tax Id", Inflector.humanize("TaxId"));
  }

}
