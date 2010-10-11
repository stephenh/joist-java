package joist.web.pages;

import junit.framework.Assert;

public class FlashPageTest extends AbstractClickPageTest {

  public void testFlashOnRedirect() throws Exception {
    Assert.assertEquals("/flash.htm", this.request("/flash.htm").set("_formId", "form").get().getRedirect());
    Assert.assertEquals(" success ", this.request("/flash.htm").getBody());
    Assert.assertEquals(" no message ", this.request("/flash.htm").getBody());
  }

}
