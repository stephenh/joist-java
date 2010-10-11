package joist.web.pages.controls.form;

import joist.util.Join;
import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

public class TwoFormsPageTest extends AbstractClickPageTest {

  public void testInitialGet() throws Exception {
    Assert.assertEquals(Join.lines(
      "",
      "<div class=\"web-form\">",
      "<form method=\"post\">",
      "<input type=\"hidden\" name=\"_formId\" value=\"form1\" />",
      "<h2>Form 1</h2>",
      "<table>",
      "<tr>",
      "<th>Value</th>",
      "<td><input id=\"form1-value\" name=\"value\" type=\"text\" value=\"foo\"/></td>",
      "<td>&nbsp;</td>",
      "</tr>",
      "</table>",
      "</form>",
      "</div>",
      "",
      "",
      "<div class=\"web-form\">",
      "<form method=\"post\">",
      "<input type=\"hidden\" name=\"_formId\" value=\"form2\" />",
      "<h2>Form 2</h2>",
      "<table>",
      "<tr>",
      "<th>Value</th>",
      "<td><input id=\"form2-value\" name=\"value\" type=\"text\" value=\"foo\"/></td>",
      "<td>&nbsp;</td>",
      "</tr>",
      "</table>",
      "</form>",
      "</div>",
      "",
      ""), this.request("/controls/form/twoForms.htm").getBody());
  }

  public void testPost() throws Exception {
    Assert.assertEquals(Join.lines(
      "",
      "<div class=\"web-form\">",
      "<form method=\"post\">",
      "<input type=\"hidden\" name=\"_formId\" value=\"form1\" />",
      "<h2>Form 1</h2>",
      "<table>",
      "<tr>",
      "<th>Value</th>",
      "<td><input id=\"form1-value\" name=\"value\" type=\"text\" value=\"bar\"/></td>",
      "<td>&nbsp;</td>",
      "</tr>",
      "</table>",
      "</form>",
      "</div>",
      "",
      "",
      "<div class=\"web-form\">",
      "<form method=\"post\">",
      "<input type=\"hidden\" name=\"_formId\" value=\"form2\" />",
      "<h2>Form 2</h2>",
      "<table>",
      "<tr>",
      "<th>Value</th>",
      "<td><input id=\"form2-value\" name=\"value\" type=\"text\" value=\"foo\"/></td>", // is still foo and not bar
      "<td>&nbsp;</td>",
      "</tr>",
      "</table>",
      "</form>",
      "</div>",
      "",
      ""), this.request("/controls/form/twoForms.htm").set("_formId", "form1").set("value", "bar").postBody());
  }
}
