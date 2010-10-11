package joist.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class TestCounters {

  private static final Set<TestCounter> counters = new LinkedHashSet<TestCounter>();

  public static void register(TestCounter c) {
    counters.add(c);
  }

  public static void resetAll() {
    for (TestCounter c : counters) {
      c.reset();
    }
  }

}
