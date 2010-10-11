package joist.web.pages;

import junit.framework.Assert;

public class AddModelAllControlsTest extends AbstractClickPageTest {

  public void testFormOnlyInOnInit() throws Exception {
    String body = this.request("/addModelAllControls.htm").getBody();
    Assert.assertTrue(body.contains("<h2>Foo</h2>"));
  }

  public void testFormFieldOnlyInOnInit() throws Exception {
    String body = this.request("/addModelAllControls.htm").set("onlyField", "true").getBody();
    Assert.assertEquals("<input id=\"foo-bar\" name=\"bar\" type=\"text\" value=\"\"/>\n", body);
  }

}
