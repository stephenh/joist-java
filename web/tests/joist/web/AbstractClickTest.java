package joist.web;

import joist.util.TestCounters;
import junit.framework.TestCase;

public abstract class AbstractClickTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
    }

}
