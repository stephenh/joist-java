package click.pages.stages.pageResolution;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class SimpleSubTest extends AbstractClickPageTest {

    public void testHelloWorldFromSubDirectory() throws Exception {
        Assert.assertEquals("Hello World from sub.", this.request("/stages/pageResolution/simpleSub.htm").getBody());
    }

}
