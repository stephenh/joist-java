package app.pages;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class DifferentBasePackageNameTest extends AbstractClickPageTest {

    public void setUp() throws Exception {
        super.setUp();
        // this.config.setBasePackageName("app.pages");
    }

    public void testHelloWorldFromRoot() throws Exception {
        Assert.assertEquals("Hello World from app.", this.request("/differentBasePackageName.htm").getBody());
    }

}
