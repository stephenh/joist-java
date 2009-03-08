package click.pages.controls.form;

import junit.framework.Assert;

import org.exigencecorp.util.Join;

import click.pages.AbstractClickPageTest;

public class TwoFormsPageTest extends AbstractClickPageTest {

    public void testInitialGet() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<form method=\"post\">",
            "<input type=\"hidden\" name=\"_formId\" value=\"form1\" />",
            "<p class=\"clickFormHeading\">Form 1</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"foo\"/></td>",
            "</tr>",
            "</table>",
            "</form>",
            "",
            "",
            "<form method=\"post\">",
            "<input type=\"hidden\" name=\"_formId\" value=\"form2\" />",
            "<p class=\"clickFormHeading\">Form 2</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"foo\"/></td>",
            "</tr>",
            "</table>",
            "</form>",
            "",
            ""), this.request("/controls/form/twoForms.htm").getBody());
    }

    public void testPost() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<form method=\"post\">",
            "<input type=\"hidden\" name=\"_formId\" value=\"form1\" />",
            "<p class=\"clickFormHeading\">Form 1</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"bar\"/></td>",
            "</tr>",
            "</table>",
            "</form>",
            "",
            "",
            "<form method=\"post\">",
            "<input type=\"hidden\" name=\"_formId\" value=\"form2\" />",
            "<p class=\"clickFormHeading\">Form 2</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"foo\"/></td>", // is still foo and not bar
            "</tr>",
            "</table>",
            "</form>",
            "",
            ""), this.request("/controls/form/twoForms.htm").set("_formId", "form1").set("Value", "bar").postBody());
    }
}
