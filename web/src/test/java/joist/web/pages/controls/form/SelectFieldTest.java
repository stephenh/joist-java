package joist.web.pages.controls.form;

import joist.util.Join;
import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

public class SelectFieldTest extends AbstractClickPageTest {

  public void testGet() throws Exception {
    Assert.assertTrue(this
      .request("/controls/form/selectField.htm")
      .getBody()
      .contains(
        Join.lines(
          "<td><select id=\"form-value\" name=\"value\">",
          "<option id=\"form-value-0\" value=\"one\">one</option>",
          "<option id=\"form-value-1\" value=\"two\">two</option>",
          "</select>",
          "</td>",
          "")));
  }

  public void testPostValueOne() throws Exception {
    Assert.assertTrue(this
      .request("/controls/form/selectField.htm")
      .set("_formId", "form")
      .set("value", "one")
      .getBody()
      .contains(
        Join.lines(
          "<td><select id=\"form-value\" name=\"value\">",
          "<option id=\"form-value-0\" selected=\"selected\" value=\"one\">one</option>",
          "<option id=\"form-value-1\" value=\"two\">two</option>",
          "</select>",
          "</td>",
          "")));
  }

  public void testGetWithOptionAlreadySet() throws Exception {
    Assert.assertTrue(this
      .request("/controls/form/selectField.htm")
      .set("value", "one")
      .getBody()
      .contains(
        Join.lines(
          "<td><select id=\"form-value\" name=\"value\">",
          "<option id=\"form-value-0\" selected=\"selected\" value=\"one\">one</option>",
          "<option id=\"form-value-1\" value=\"two\">two</option>",
          "</select>",
          "</td>",
          "")));
  }

  public void testGetWithBlank() throws Exception {
    Assert.assertTrue(this
      .request("/controls/form/selectField.htm")
      .set("flipShowBlank", "true")
      .getBody()
      .contains(
        Join.lines(
          "<td><select id=\"form-value\" name=\"value\">",
          "<option id=\"form-value-blank\" selected=\"selected\" value=\"\"></option>",
          "<option id=\"form-value-0\" value=\"one\">one</option>",
          "<option id=\"form-value-1\" value=\"two\">two</option>",
          "</select>",
          "</td>",
          "")));
  }

  public void testPostWithBlank() throws Exception {
    Assert.assertTrue(this
      .request("/controls/form/selectField.htm")
      //
      .set("value", "one")
      .set("flipShowBlank", "true")
      .set("_formId", "form")
      .set("value", "")
      .getBody()
      .contains(
        Join.lines(
          "<td><select id=\"form-value\" name=\"value\">",
          "<option id=\"form-value-blank\" selected=\"selected\" value=\"\"></option>",
          "<option id=\"form-value-0\" value=\"one\">one</option>",
          "<option id=\"form-value-1\" value=\"two\">two</option>",
          "</select>",
          "</td>",
          "")));
  }

}
