package click.controls;

import java.io.StringWriter;

import org.apache.velocity.app.VelocityEngine;

import click.AbstractClickTest;
import click.ClickConfig;
import click.ClickContext;
import click.Control;
import click.CurrentContext;
import click.fakedomain.EmployeeToFriendlyStringConverter;
import click.fakedomain.EmployeeToStringConverter;
import click.util.HtmlWriter;

public abstract class AbstractClickControlTest extends AbstractClickTest {

    private static VelocityEngine cachedEngine;
    protected ClickConfig config;

    public void setUp() throws Exception {
        super.setUp();
        this.config = new ClickConfig("click.pages") {
            @Override
            protected VelocityEngine createVelocityEngine() {
                // Creating a new engine for each test was causing heap errors
                if (AbstractClickControlTest.cachedEngine == null) {
                    AbstractClickControlTest.cachedEngine = super.createVelocityEngine();
                }
                return AbstractClickControlTest.cachedEngine;
            }
        };
        this.config.getUrlConverterRegistry().addConverter(new EmployeeToStringConverter());
        this.config.getTextConverterRegistry().addConverter(new EmployeeToFriendlyStringConverter());
        CurrentContext.set(new ClickContext(null, this.config, null, null));
    }

    protected String render(Control c) {
        StringWriter s = new StringWriter();
        c.render(new HtmlWriter(s));
        return s.toString();
    }

}
