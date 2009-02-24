package app.pages;

import junit.framework.Assert;
import click.ClickConfig;
import click.pages.AbstractClickPageTest;

public class DifferentBasePackageNameTest extends AbstractClickPageTest {

    public void setUp() throws Exception {
        this.config = new ClickConfig("app.pages");
        super.setUp();
    }

    public void testHelloWorldFromRoot() throws Exception {
        Assert.assertEquals("Hello World from app.", this.request("/differentBasePackageName.htm").getBody());
    }

}
