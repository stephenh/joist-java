package joist.web.controls.form;

import joist.web.controls.AbstractClickControlTest;
import junit.framework.Assert;

import org.bindgen.java.lang.IntegerBinding;

public class StaticFieldTest extends AbstractClickControlTest {

  public void testGet() {
    StaticField t = new StaticField().id("t");
    t.setParent(new Form("form"));
    Assert.assertEquals("<span id=\"form-t\"></span>", this.render(t));
  }

  public void testIntegerBindingDoesNothing() {
    this.request.setParameter("t", "2");
    IntegerBinding ib = new IntegerBinding(1);
    Assert.assertEquals(new Integer(1), ib.get());

    StaticField t = new StaticField(ib).id("t");
    t.onProcess();
    // Still 1--ignored the form post
    Assert.assertEquals(new Integer(1), ib.get());
    Assert.assertEquals("<span id=\"t\">1</span>", this.render(t));
  }

}
