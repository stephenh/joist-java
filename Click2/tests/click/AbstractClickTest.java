package click;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.exigencecorp.util.TestCounters;

public abstract class AbstractClickTest extends TestCase {

    static {
        BasicConfigurator.configure();
    }

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
    }

}
