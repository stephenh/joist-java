package click.controls;

import junit.framework.Assert;
import click.AbstractPage;
import click.CurrentContext;
import click.controls.form.Form;

public class ContainerTest extends AbstractClickControlTest {

    private AbstractPage page;

    public void setUp() throws Exception {
        super.setUp();
        this.page = new AbstractPage() {
        };
    }

    public void testWithForm() {
        CurrentContext.get().setPage(this.page);
        Form f = new Form("f");
        Assert.assertEquals(true, this.page.getControls().contains(f));
    }

}
