package joist.web.pages;

import junit.framework.Assert;

public class RedirectOnInitTest extends AbstractClickPageTest {

    public void testHelloWorldFromRoot() throws Exception {
        Assert.assertEquals("/foo.htm", this.request("/redirectOnInit.htm").getRedirect());
        Assert.assertEquals(0, RedirectOnInitPage.rendered.get());
    }

}
