package joist.web.controls.form;

import joist.web.controls.AbstractClickControlTest;
import junit.framework.Assert;
import bindgen.java.lang.StringBinding;

public class TextAreaTest extends AbstractClickControlTest {

    public void testGet() {
        TextArea t = new TextArea().id("t");
        t.setParent(new Form("form"));
        Assert.assertEquals("<textarea id=\"form-t\" name=\"t\" rows=\"20\" cols=\"80\">text</textarea>", this.render(t));
    }

    public void testEmptyStringBindsAsNull() {
        this.request.setParameter("t", "");
        StringBinding sb = new StringBinding("foo");
        Assert.assertEquals("foo", sb.get());

        TextArea t = new TextArea(sb).id("t");
        t.onProcess();
        Assert.assertEquals(null, sb.get());
    }

}
