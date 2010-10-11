package joist.util;

import java.util.concurrent.atomic.AtomicInteger;

public class TestCounter {

  private final AtomicInteger current = new AtomicInteger();

  public TestCounter() {
    TestCounters.register(this);
  }

  public int next() {
    return this.current.incrementAndGet();
  }

  public int tick() {
    return this.current.incrementAndGet();
  }

  public int get() {
    return this.current.get();
  }

  public void reset() {
    this.current.set(0);
  }
}
