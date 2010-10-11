package joist.web;

import joist.util.TestCounters;
import junit.framework.TestCase;

import org.apache.velocity.app.VelocityEngine;

public abstract class AbstractClickTest extends TestCase {

  public static VelocityEngine testEngine;

  static {
    VelocityEngine engine = new VelocityEngine();
    engine.setProperty("resource.loader", "class");
    engine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    try {
      engine.init();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    testEngine = engine;
  }

  public void setUp() throws Exception {
    super.setUp();
    TestCounters.resetAll();
  }

}
