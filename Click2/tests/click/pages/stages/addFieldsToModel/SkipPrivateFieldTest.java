package click.pages.stages.addFieldsToModel;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class SkipPrivateFieldTest extends AbstractClickPageTest {

    public void testPrivateStringFieldDoesNotGetBound() throws Exception {
        Assert.assertEquals(" skipped ", this.request("/stages/addFieldsToModel/skipPrivateField.htm").getBody());
    }

}
