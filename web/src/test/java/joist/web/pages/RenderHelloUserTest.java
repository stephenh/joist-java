package joist.web.pages;

import junit.framework.Assert;

public class RenderHelloUserTest extends AbstractClickPageTest {

  public void testHelloWorldFromRoot() throws Exception {
    Assert.assertEquals("Hello bob.", this.request("/renderHelloUser.htm").getBody());
  }

}
