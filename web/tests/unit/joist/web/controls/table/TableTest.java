package joist.web.controls.table;

import joist.util.Copy;
import joist.web.controls.AbstractClickControlTest;
import junit.framework.Assert;

public class TableTest extends AbstractClickControlTest {

    public void testCurrentBindingNotSet() {
        Table<String> t = new Table<String>("table");
        t.setList(Copy.list("foo"));
        try {
            this.render(t);
            Assert.fail();
        } catch (RuntimeException re) {
            Assert.assertEquals("The current binding is not set", re.getMessage());
        }
    }

}
