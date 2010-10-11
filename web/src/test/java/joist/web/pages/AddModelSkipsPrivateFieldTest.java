package joist.web.pages;

import junit.framework.Assert;

public class AddModelSkipsPrivateFieldTest extends AbstractClickPageTest {

  public void testPrivateStringFieldDoesNotGetBound() throws Exception {
    Assert.assertEquals(" skipped ", this.request("/addModelSkipsPrivateField.htm").getBody());
  }

}
