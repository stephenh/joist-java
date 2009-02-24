package click.controls;

import junit.framework.Assert;
import junit.framework.TestCase;
import click.AbstractPage;
import click.ClickContext;
import click.CurrentContext;
import click.controls.form.Form;

public class AddControlTest extends TestCase {

    private AbstractPage page;
    private ClickContext context;

    public void setUp() {
        this.context = new ClickContext();
        this.page = new AbstractPage() {
        };
        this.context.setPage(this.page);
        CurrentContext.set(this.context);
    }

    public void testWithForm() {
        Form f = new Form();
        Assert.assertEquals(true, this.page.getControls().contains(f));
    }
}
