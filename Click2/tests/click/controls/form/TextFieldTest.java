package click.controls.form;

import junit.framework.Assert;
import bindgen.java.lang.StringBinding;
import click.controls.AbstractClickControlTest;

public class TextFieldTest extends AbstractClickControlTest {

    public void testEmptyStringBindsAsNull() {
        this.request.setParameter("t", "");
        StringBinding sb = new StringBinding("foo");
        Assert.assertEquals("foo", sb.get());

        TextField t = new TextField(sb).id("t");
        t.onProcess();
        Assert.assertEquals(null, sb.get());
    }

}
