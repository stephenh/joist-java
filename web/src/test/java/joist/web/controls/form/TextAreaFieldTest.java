package joist.web.controls.form;

import joist.web.controls.AbstractClickControlTest;
import junit.framework.Assert;

import org.bindgen.java.lang.StringBinding;

public class TextAreaFieldTest extends AbstractClickControlTest {

  public void testGet() {
    TextAreaField t = new TextAreaField().id("t");
    t.setParent(new Form("form"));
    Assert.assertEquals("<textarea id=\"form-t\" name=\"t\" rows=\"20\" cols=\"80\"></textarea>", this.render(t));
  }

  public void testEmptyStringBindsAsNull() {
    this.request.setParameter("t", "");
    StringBinding sb = new StringBinding("foo");
    Assert.assertEquals("foo", sb.get());

    TextAreaField t = new TextAreaField(sb).id("t");
    t.onProcess();
    Assert.assertEquals(null, sb.get());
  }

}
