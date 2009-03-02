package org.exigencecorp.util;

public class TestCounter {

    private int current = 0;

    public TestCounter() {
        TestCounters.register(this);
    }

    public int next() {
        return ++this.current;
    }

    public void reset() {
        this.current = 0;
    }
}
