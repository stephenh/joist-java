package joist.web.pages;

import junit.framework.Assert;

public class RedirectOnClickTest extends AbstractClickPageTest {

  public void testGetDoesNotCauseRedirect() throws Exception {
    Assert.assertEquals(null, this.request("/redirectOnClick.htm").getRedirect());
  }

  public void testPostDoesCauseRedirect() throws Exception {
    Assert.assertEquals("/foo.htm", this.request("/redirectOnClick.htm").set("_formId", "form").getRedirect());
    Assert.assertEquals(0, RedirectOnClickPage.rendered.get());
    Assert.assertEquals(0, RedirectOnClickPage.lineAfterRedirect.get());
  }
}
