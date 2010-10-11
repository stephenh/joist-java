package joist.web.pages;

import junit.framework.Assert;

public class RedirectOnInitTest extends AbstractClickPageTest {

  public void testHelloWorldFromRoot() throws Exception {
    Assert.assertEquals("/redirectOnInit.htm", this.request("/redirectOnInit.htm").getRedirect());
    Assert.assertEquals(0, RedirectOnInitPage.rendered.get());
  }

  public void testHelloWorldFromRootWithContextPath() throws Exception {
    Assert.assertEquals("/app/redirectOnInit.htm", this.request("/redirectOnInit.htm").contextPath("/app").getRedirect());
    Assert.assertEquals(0, RedirectOnInitPage.rendered.get());
  }

}
