package joist.util;

import java.util.Properties;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SystemPropertiesTest extends TestCase {

  public void testOverride() {
    Properties p = new Properties();
    p.setProperty("foo", "a");
    p.setProperty("foo.dev", "b");
    SystemProperties.overrideForSuffix("dev", p);
    Assert.assertEquals("b", p.getProperty("foo"));
  }

  public void testOverrideIsNullSafe() {
    Properties p = new Properties();
    p.setProperty("foo", "a");
    p.setProperty("foo.dev", "b");
    p.setProperty("foo.", "b");
    SystemProperties.overrideForSuffix(null, p);
    Assert.assertEquals("a", p.getProperty("foo"));
  }

}
