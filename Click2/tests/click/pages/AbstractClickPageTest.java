package click.pages;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;

import servletTest.RequestBuilder;
import click.ClickConfig;
import click.ClickServlet;
import click.stages.pageResolution.PageResolutionStage;

public abstract class AbstractClickPageTest extends TestCase {

    protected ClickConfig config = new ClickConfig();
    protected ClickServlet clickServlet = new ClickServlet(this.config);

    static {
        BasicConfigurator.configure();
    }

    public void setUp() throws Exception {
        this.config.get(PageResolutionStage.class).setBasePackageName("click.pages");
        this.clickServlet.init();
    }

    public RequestBuilder request(String url) throws Exception {
        return new RequestBuilder(this.clickServlet, url);
    }

}
