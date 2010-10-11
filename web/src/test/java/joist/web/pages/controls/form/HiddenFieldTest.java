package joist.web.pages.controls.form;

import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

public class HiddenFieldTest extends AbstractClickPageTest {

  public void testGet() throws Exception {
    Assert.assertTrue(//
      this.request("/controls/form/hiddenField.htm").getBody().contains("<input id=\"form-employee\" name=\"employee\" type=\"hidden\" value=\"\"/>"));
  }

  public void testPostValueOne() throws Exception {
    Assert.assertTrue(this
      .request("/controls/form/hiddenField.htm")
      .set("_formId", "form")
      .set("employee", "2")
      .getBody()
      .contains("<input id=\"form-employee\" name=\"employee\" type=\"hidden\" value=\"2\"/>"));
  }
}
