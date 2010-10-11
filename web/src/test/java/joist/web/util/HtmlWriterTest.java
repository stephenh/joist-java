package joist.web.util;

import java.io.StringWriter;

import joist.util.Copy;
import joist.web.controls.form.TextField;
import junit.framework.Assert;
import junit.framework.TestCase;

public class HtmlWriterTest extends TestCase {

  private StringWriter s;
  private HtmlWriter w;

  public void setUp() {
    this.s = new StringWriter();
    this.w = new HtmlWriter(this.s);
  }

  public void testAlone() {
    this.w.append("{}", "bar");
    Assert.assertEquals("bar", this.s.toString());
  }

  public void testAtEnd() {
    this.w.append("foo{}", "bar");
    Assert.assertEquals("foobar", this.s.toString());
  }

  public void testAtBegining() {
    this.w.append("{}foo", "bar");
    Assert.assertEquals("barfoo", this.s.toString());
  }

  public void testOneNotWrapped() {
    this.w.line("<p>{}</p>", "bar");
    Assert.assertEquals("<p>bar</p>\n", this.s.toString());
  }

  public void testThree() {
    this.w.append("{}-{}-{}", "one", "two", "three");
    Assert.assertEquals("one-two-three", this.s.toString());
  }

  public void testOneWrapped() {
    this.w.line("<p id={}></p>", "bar");
    Assert.assertEquals("<p id=\"bar\"></p>\n", this.s.toString());
  }

  public void testOneWrappedThenOneNotWrapped() {
    this.w.line("<p id={}>{}</p>", "bar", "bar");
    Assert.assertEquals("<p id=\"bar\">bar</p>\n", this.s.toString());
  }

  public void testOneNotWrappedThenOneWrapped() {
    this.w.line("{}<p id={}></p>", "bar", "bar");
    Assert.assertEquals("bar<p id=\"bar\"></p>\n", this.s.toString());
  }

  public void testOneTextField() {
    this.w.line("<p>{}</p>", new TextField().id("foo"));
    Assert.assertEquals("<p><input id=\"foo\" name=\"foo\" type=\"text\" value=\"\"/></p>\n", this.s.toString());
  }

  public void testWithMap() {
    this.w.line("<p{}>content</p>", Copy.map("class", "foo"));
    Assert.assertEquals("<p class=\"foo\">content</p>\n", this.s.toString());
  }

  public void testWithMapThatIsNull() {
    this.w.line("<p{}>content</p>", (Object[]) null);
    Assert.assertEquals("<p>content</p>\n", this.s.toString());
  }

  public void testWithMapThatIsNullWithOtherArguments() {
    this.w.line("<p id={}{}>content</p>", "one", null);
    Assert.assertEquals("<p id=\"one\">content</p>\n", this.s.toString());
  }

  public void testNoNpe() {
    this.w.append(null);
  }

}
