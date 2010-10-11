package joist.web.pages;

import junit.framework.Assert;

public class HelloWorldTest extends AbstractClickPageTest {

  public void testHelloWorldFromRoot() throws Exception {
    Assert.assertEquals("Hello World.", this.request("/helloWorld.htm").getBody());
  }

}
