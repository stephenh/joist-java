package click.pages.stages.addFieldsToModel;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class AddPublicFieldTest extends AbstractClickPageTest {

    public void testPublicStringFieldGetsBound() throws Exception {
        Assert.assertEquals("Hello foo.", this.request("/stages/addFieldsToModel/addPublicField.htm").getBody());
    }

}
