package click.pages;

import junit.framework.Assert;

public class SetPublicFieldTest extends AbstractClickPageTest {

    public void testPublicStringFieldGetsBound() throws Exception {
        Assert.assertEquals("Hello foo.", this.request("/setPublicField.htm").set("value", "foo").getBody());
    }

}
