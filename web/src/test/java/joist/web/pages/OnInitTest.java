package joist.web.pages;

import junit.framework.Assert;

public class OnInitTest extends AbstractClickPageTest {

  public void testHelloWorldFromRoot() throws Exception {
    Assert.assertEquals("Set foo in onInit().", this.request("/onInit.htm").getBody());
  }

}
