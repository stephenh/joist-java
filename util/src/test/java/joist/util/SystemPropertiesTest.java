package joist.util;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class SystemPropertiesTest {

  @Test
  public void testOverride() {
    Properties p = new Properties();
    p.setProperty("foo", "a");
    p.setProperty("foo.dev", "b");
    SystemProperties.overrideForSuffix("dev", p);
    Assert.assertEquals("b", p.getProperty("foo"));
  }

  @Test
  public void testOverrideIsNullSafe() {
    Properties p = new Properties();
    p.setProperty("foo", "a");
    p.setProperty("foo.dev", "b");
    p.setProperty("foo.", "b");
    SystemProperties.overrideForSuffix(null, p);
    Assert.assertEquals("a", p.getProperty("foo"));
  }

}
