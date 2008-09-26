package click.pages.stages.onInit;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class OnInitTest extends AbstractClickPageTest {

    public void testHelloWorldFromRoot() throws Exception {
        Assert.assertEquals("Set foo in onInit().", this.request("/stages/onInit/onInit.htm").getBody());
    }

}
