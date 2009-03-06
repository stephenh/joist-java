package click.controls;

import click.AbstractClickTest;
import click.ClickConfig;
import click.ClickContext;
import click.CurrentContext;

public abstract class AbstractClickControlTest extends AbstractClickTest {

    protected ClickConfig config = new ClickConfig("click.pages");

    public void setUp() throws Exception {
        super.setUp();
        CurrentContext.set(new ClickContext(null, this.config, null, null));
    }

}
