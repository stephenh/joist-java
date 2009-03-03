package click.pages;

import junit.framework.Assert;

public class FlashPageTest extends AbstractClickPageTest {

    public void testFlashOnRedirect() throws Exception {
        Assert.assertEquals(" submitted ", this.request("/flash.htm").setParameter("_formId", "form").getBody());
    }

}
