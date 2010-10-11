package joist.web.pages;

import junit.framework.Assert;

public class AddModelPublicFieldTest extends AbstractClickPageTest {

  public void testPublicStringFieldGetsBound() throws Exception {
    Assert.assertEquals("Hello foo.", this.request("/addModelPublicField.htm").getBody());
  }

}
