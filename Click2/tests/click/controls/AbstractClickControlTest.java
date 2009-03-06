package click.controls;

import click.AbstractClickTest;
import click.ClickConfig;
import click.ClickContext;
import click.CurrentContext;

public abstract class AbstractClickControlTest extends AbstractClickTest {

    protected ClickConfig config;

    public void setUp() throws Exception {
        super.setUp();
        this.config = new ClickConfig("click.pages");
        CurrentContext.set(new ClickContext(null, this.config, null, null));
    }

}
