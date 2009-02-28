package click.pages.controls.form;

import junit.framework.Assert;

import org.exigencecorp.util.Join;

import click.pages.AbstractClickPageTest;

public class TextFieldTest extends AbstractClickPageTest {

    public void testInitialGet() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<form>",
            "<p class=\"clickFormHeading\">null</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"foo\"/></td>",
            "</tr>",
            "</table>",
            "</form>",
            "",
            ""), this.request("/controls/form/textField.htm").getBody());
    }

    public void testPost() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<form>",
            "<p class=\"clickFormHeading\">null</p>",
            "<table class=\"clickForm\">",
            "<tr>",
            "<th>Value</th>",
            "<td><input id=\"Value\" name=\"Value\" type=\"text\" value=\"bar\"/></td>",
            "</tr>",
            "</table>",
            "</form>",
            "",
            ""), this.request("/controls/form/textField.htm").setParameter("form_name", "form").setParameter("Value", "bar").postBody());
    }
}
