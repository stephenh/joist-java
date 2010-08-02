package joist.util;

import joist.util.TestCounter;
import joist.util.TestCounters;
import junit.framework.Assert;
import junit.framework.TestCase;

public class TestCounterTest extends TestCase {

  public void testResetAll() {
    TestCounter c = new TestCounter();
    Assert.assertEquals(1, c.next());
    Assert.assertEquals(2, c.next());

    TestCounters.resetAll();
    Assert.assertEquals(1, c.next());
    Assert.assertEquals(2, c.next());
    Assert.assertEquals(2, c.get());
  }

}
