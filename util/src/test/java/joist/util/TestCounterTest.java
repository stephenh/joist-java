package joist.util;

import org.junit.Assert;
import org.junit.Test;

public class TestCounterTest {

  @Test
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
