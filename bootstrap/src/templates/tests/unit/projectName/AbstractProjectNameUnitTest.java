package @projectName@;

import joist.domain.util.Freeze;
import junit.framework.TestCase;

public abstract class Abstract@ProjectName@UnitTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        Freeze.at(2008, 1, 1);
    }

}
