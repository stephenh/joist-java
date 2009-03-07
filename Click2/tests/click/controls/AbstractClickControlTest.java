package click.controls;

import java.io.StringWriter;

import click.AbstractClickTest;
import click.ClickConfig;
import click.ClickContext;
import click.Control;
import click.CurrentContext;
import click.util.HtmlWriter;

public abstract class AbstractClickControlTest extends AbstractClickTest {

    protected ClickConfig config;

    public void setUp() throws Exception {
        super.setUp();
        this.config = new ClickConfig("click.pages");
        CurrentContext.set(new ClickContext(null, this.config, null, null));
    }

    protected String render(Control c) {
        StringWriter s = new StringWriter();
        c.render(new HtmlWriter(s));
        return s.toString();
    }

}
