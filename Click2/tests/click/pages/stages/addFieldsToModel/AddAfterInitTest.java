package click.pages.stages.addFieldsToModel;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class AddAfterInitTest extends AbstractClickPageTest {

    public void testPublicStringFieldGetsBound() throws Exception {
        Assert.assertEquals("Hello init.", this.request("/stages/addFieldsToModel/addAfterInit.htm").getBody());
    }

}
