package click.pages.stages.setFieldsFromRequest;

import junit.framework.Assert;
import click.pages.AbstractClickPageTest;

public class SetPublicFieldTest extends AbstractClickPageTest {

    public void testPublicStringFieldGetsBound() throws Exception {
        Assert.assertEquals("Hello foo.", this.request("/stages/setFieldsFromRequest/setPublicField.htm").setParameter("value", "foo").getBody());
    }

}
