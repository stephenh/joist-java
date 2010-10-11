package joist.web.pages.controls.form;

import joist.util.Join;
import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

public class TextFieldTest extends AbstractClickPageTest {

  public void testInitialGetDoesNotCauseAClick() throws Exception {
    this.request("/controls/form/textField.htm").get();
    Assert.assertEquals(0, TextFieldPage.submitted.get());
  }

  public void testInitialGet() throws Exception {
    Assert.assertEquals(Join.lines(
      "",
      "<div class=\"web-form\">",
      "<form method=\"post\">",
      "<input type=\"hidden\" name=\"_formId\" value=\"form\" />",
      "<h2>Form</h2>",
      "<table>",
      "<tr>",
      "<th>Value</th>",
      "<td><input id=\"form-value\" name=\"value\" type=\"text\" value=\"foo\"/></td>",
      "<td>&nbsp;</td>",
      "</tr>",
      "<tr>",
      "<th>&nbsp;</th>",
      "<td>",
      "<input id=\"form-submit\" name=\"_formButton\" type=\"submit\" value=\"Submit\"/>",
      "</td>",
      "<td>&nbsp;</td>",
      "</tr>",
      "</table>",
      "</form>",
      "</div>",
      "",
      ""), this.request("/controls/form/textField.htm").getBody());
  }

  public void testPost() throws Exception {
    Assert.assertEquals(Join.lines(
      "",
      "<div class=\"web-form\">",
      "<form method=\"post\">",
      "<input type=\"hidden\" name=\"_formId\" value=\"form\" />",
      "<h2>Form</h2>",
      "<table>",
      "<tr>",
      "<th>Value</th>",
      "<td><input id=\"form-value\" name=\"value\" type=\"text\" value=\"bar\"/></td>",
      "<td>&nbsp;</td>",
      "</tr>",
      "<tr>",
      "<th>&nbsp;</th>",
      "<td>",
      "<input id=\"form-submit\" name=\"_formButton\" type=\"submit\" value=\"Submit\"/>",
      "</td>",
      "<td>&nbsp;</td>",
      "</tr>",
      "</table>",
      "</form>",
      "</div>",
      "",
      ""), this.request("/controls/form/textField.htm").set("_formId", "form").set("value", "bar").postBody());
  }
}
