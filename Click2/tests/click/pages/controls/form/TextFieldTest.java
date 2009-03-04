package click.pages.controls.form;

import junit.framework.Assert;

import org.exigencecorp.util.Join;

import click.pages.AbstractClickPageTest;

public class TextFieldTest extends AbstractClickPageTest {

    public void testInitialGetDoesNotCauseAClick() throws Exception {
        this.request("/controls/form/textField.htm").get();
        Assert.assertEquals(0, TextFieldPage.submitted.get());
    }

    public void testInitialGet() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<form>",
            "<input type=\"hidden\" name=\"_formId\" value=\"form\" />",
            "<p class=\"clickFormHeading\">Form</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"foo\"/></td>",
            "</tr>",
            "<tr><th>&nbsp;</th><td>",
            "<input id=\"Submit\" name=\"Submit\" type=\"submit\" value=\"Submit\"/>",
            "</td></tr>",
            "</table>",
            "</form>",
            "",
            ""), this.request("/controls/form/textField.htm").getBody());
    }

    public void testPost() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<form>",
            "<input type=\"hidden\" name=\"_formId\" value=\"form\" />",
            "<p class=\"clickFormHeading\">Form</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"bar\"/></td>",
            "</tr>",
            "<tr><th>&nbsp;</th><td>",
            "<input id=\"Submit\" name=\"Submit\" type=\"submit\" value=\"Submit\"/>",
            "</td></tr>",
            "</table>",
            "</form>",
            "",
            ""), this.request("/controls/form/textField.htm").set("_formId", "form").set("Value", "bar").postBody());
    }
}
