package joist.web.pages;

import joist.util.Join;
import junit.framework.Assert;

public class LayoutTest extends AbstractClickPageTest {

  public void testHelloWorldFromRoot() throws Exception {
    Assert.assertEquals(Join.lines("left", //
      "the main page",
      "right"), this.request("/layout.htm").getBody());
  }

}
