package click;

import junit.framework.TestCase;

import org.exigencecorp.util.TestCounters;

public abstract class AbstractClickTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
    }

}
