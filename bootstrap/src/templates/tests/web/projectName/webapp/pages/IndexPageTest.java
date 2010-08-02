package @projectName@.webapp.pages;

import @projectName@.Abstract@ProjectName@WebTest;
import @projectName@.webapp.pageObjects.IndexPageObject;

public class IndexPageTest extends Abstract@ProjectName@WebTest {

    private IndexPageObject index;

    public void setUp() throws Exception {
        super.setUp();
        this.commitAndReOpen();
    }

    public void testStart() {
        this.index = this.startAtIndexPage();
    }

}
