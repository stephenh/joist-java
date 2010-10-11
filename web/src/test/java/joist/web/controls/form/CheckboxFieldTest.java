package joist.web.controls.form;

import joist.web.controls.AbstractClickControlTest;
import junit.framework.Assert;

import org.bindgen.java.lang.BooleanBinding;

public class CheckboxFieldTest extends AbstractClickControlTest {

  public void testFalseToTrue() {
    BooleanBinding bb = new BooleanBinding(false);
    Assert.assertEquals(false, bb.get().booleanValue());

    CheckboxField c = new CheckboxField(bb).id("c");
    Assert.assertEquals("<input id=\"c\" name=\"c\" type=\"checkbox\" value=\"true\"/>", this.render(c));

    this.request.setParameter("c", "true");
    c.onProcess();
    Assert.assertEquals(true, bb.get().booleanValue());
    Assert.assertEquals("<input id=\"c\" name=\"c\" type=\"checkbox\" value=\"true\" checked=\"checked\"/>", this.render(c));
  }

  public void testTrueToFalse() {
    BooleanBinding bb = new BooleanBinding(true);
    Assert.assertEquals(true, bb.get().booleanValue());

    CheckboxField c = new CheckboxField(bb).id("c");
    Assert.assertEquals("<input id=\"c\" name=\"c\" type=\"checkbox\" value=\"true\" checked=\"checked\"/>", this.render(c));

    // this.request.setParameter("c", "true"); Browsers will not POST any value if unchecked
    c.onProcess();
    Assert.assertEquals(false, bb.get().booleanValue());
    Assert.assertEquals("<input id=\"c\" name=\"c\" type=\"checkbox\" value=\"true\"/>", this.render(c));
  }

}
