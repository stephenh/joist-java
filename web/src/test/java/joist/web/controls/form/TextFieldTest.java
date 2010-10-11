package joist.web.controls.form;

import joist.web.controls.AbstractClickControlTest;
import junit.framework.Assert;

import org.bindgen.java.lang.IntegerBinding;
import org.bindgen.java.lang.StringBinding;

public class TextFieldTest extends AbstractClickControlTest {

  public void testEmptyStringBindsAsNull() {
    this.request.setParameter("t", "");
    StringBinding sb = new StringBinding("foo");
    Assert.assertEquals("foo", sb.get());

    TextField t = new TextField(sb).id("t");
    t.onProcess();
    Assert.assertEquals(null, sb.get());
  }

  public void testIntegerBinding() {
    this.request.setParameter("t", "2");
    IntegerBinding ib = new IntegerBinding(1);
    Assert.assertEquals(new Integer(1), ib.get());

    TextField t = new TextField(ib).id("t");
    t.onProcess();
    Assert.assertEquals(new Integer(2), ib.get());
    Assert.assertEquals("<input id=\"t\" name=\"t\" type=\"text\" value=\"2\"/>", this.render(t));
  }
}
