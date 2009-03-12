package joist.web.pages.controls.form;

import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

import org.exigencecorp.util.Join;

public class SelectFieldTest extends AbstractClickPageTest {

    public void testGet() throws Exception {
        Assert.assertTrue(this.request("/controls/form/selectField.htm").getBody().contains(
            Join.lines(
                "<td><select id=\"Value\" name=\"Value\">",
                "<option value=\"one\">one</option>",
                "<option value=\"two\">two</option>",
                "</select>",
                "</td>",
                "")));
    }

    public void testPostValueOne() throws Exception {
        Assert.assertTrue(this.request("/controls/form/selectField.htm").set("_formId", "form").set("Value", "one").getBody().contains(
            Join.lines(
                "<td><select id=\"Value\" name=\"Value\">",
                "<option selected=\"selected\" value=\"one\">one</option>",
                "<option value=\"two\">two</option>",
                "</select>",
                "</td>",
                "")));
    }

    public void testGetWithOptionAlreadySet() throws Exception {
        Assert.assertTrue(this.request("/controls/form/selectField.htm").set("value", "one").getBody().contains(
            Join.lines(
                "<td><select id=\"Value\" name=\"Value\">",
                "<option selected=\"selected\" value=\"one\">one</option>",
                "<option value=\"two\">two</option>",
                "</select>",
                "</td>",
                "")));
    }

    public void testGetWithBlank() throws Exception {
        Assert.assertTrue(this.request("/controls/form/selectField.htm").set("flipShowBlank", "true").getBody().contains(
            Join.lines(
                "<td><select id=\"Value\" name=\"Value\">",
                "<option selected=\"selected\" value=\"\"></option>",
                "<option value=\"one\">one</option>",
                "<option value=\"two\">two</option>",
                "</select>",
                "</td>",
                "")));
    }

    public void testPostWithBlank() throws Exception {
        Assert.assertTrue(this.request("/controls/form/selectField.htm")//
            .set("value", "one")
            .set("flipShowBlank", "true")
            .set("_formId", "form")
            .set("Value", "")
            .getBody()
            .contains(
                Join.lines(
                    "<td><select id=\"Value\" name=\"Value\">",
                    "<option selected=\"selected\" value=\"\"></option>",
                    "<option value=\"one\">one</option>",
                    "<option value=\"two\">two</option>",
                    "</select>",
                    "</td>",
                    "")));
    }

}
