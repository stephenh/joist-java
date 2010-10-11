package joist.web.pages;

import junit.framework.Assert;

public class AddModelAfterInitTest extends AbstractClickPageTest {

  public void testPublicStringFieldGetsBound() throws Exception {
    Assert.assertEquals("Hello init.", this.request("/addModelAfterInit.htm").getBody());
  }

}
