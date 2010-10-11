package joist.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.Map.Entry;

public class SystemProperties {

  /** Loads path into the system properties, without overwriting, and performing "*.stage" substitution. */
  public static void loadFromFileIfExists(String path) {
    if (!new File(path).exists()) {
      return;
    }
    Properties p = new Properties();
    try {
      p.load(new FileReader(path));
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
    String stage = System.getProperty("stage");
    SystemProperties.overrideForSuffix(stage, p);
    SystemProperties.setUnlessExists(p);
  }

  /** Loads path into the system properties, without overwriting, and performing "*.stage" substitution. */
  public static void loadFromClasspath(String path) {
    Properties p = new Properties();
    try {
      p.load(SystemProperties.class.getResourceAsStream(path));
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
    String stage = System.getProperty("stage");
    SystemProperties.overrideForSuffix(stage, p);
    SystemProperties.setUnlessExists(p);
  }

  public static void overrideForSuffix(String suffix, Properties p) {
    for (Entry<Object, Object> e : Copy.list(p.entrySet())) {
      String key = (String) e.getKey();
      if (key.endsWith("." + suffix)) {
        String actualKey = key.substring(0, key.length() - ("." + suffix).length());
        p.setProperty(actualKey, (String) e.getValue());
      }
    }
  }

  private static void setUnlessExists(Properties p) {
    for (Entry<Object, Object> e : p.entrySet()) {
      if (!System.getProperties().containsKey(e.getKey())) {
        System.setProperty((String) e.getKey(), (String) e.getValue());
      }
    }
  }

}
