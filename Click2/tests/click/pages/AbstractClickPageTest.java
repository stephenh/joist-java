package click.pages;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;

import servletTest.RequestBuilder;
import click.ClickConfig;
import click.ClickServlet;

public abstract class AbstractClickPageTest extends TestCase {

    protected ClickConfig config = new ClickConfig("click.pages");
    protected ClickServlet clickServlet = new ClickServlet(this.config);

    static {
        BasicConfigurator.configure();
    }

    public void setUp() throws Exception {
        this.clickServlet.init();
    }

    public RequestBuilder request(String url) throws Exception {
        return new RequestBuilder(this.clickServlet, url);
    }

}
