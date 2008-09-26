package click.pages.stages.render;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class HelloUserTest extends AbstractClickPageTest {

    public void testHelloWorldFromRoot() throws Exception {
        Assert.assertEquals("Hello bob.", this.request("/stages/render/helloUser.htm").getBody());
    }

}
