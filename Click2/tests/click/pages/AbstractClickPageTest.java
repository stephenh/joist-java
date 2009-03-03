package click.pages;

import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;
import org.exigencecorp.util.TestCounters;

import servletTest.RequestBuilder;
import servletTest.SessionStub;
import click.ClickConfig;
import click.ClickServlet;

public abstract class AbstractClickPageTest extends TestCase {

    static {
        BasicConfigurator.configure();
    }

    protected ClickConfig config = new ClickConfig("click.pages");
    protected ClickServlet clickServlet = new ClickServlet() {
        private static final long serialVersionUID = 1;

        protected ClickConfig createClickConfig() {
            return AbstractClickPageTest.this.config;
        }
    };
    private SessionStub sessionStub = new SessionStub();

    public void setUp() throws Exception {
        super.setUp();
        TestCounters.resetAll();
        this.clickServlet.init();
    }

    public RequestBuilder request(String url) throws Exception {
        RequestBuilder request = new RequestBuilder(this.clickServlet, url);
        request.getRequest().setSessionStub(this.sessionStub);
        return request;
    }

}
