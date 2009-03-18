package joist.web.pages;

import joist.web.AbstractClickTest;
import joist.web.ClickConfig;
import joist.web.ClickServlet;
import servletTest.RequestBuilder;
import servletTest.SessionStub;

public abstract class AbstractClickPageTest extends AbstractClickTest {

    protected ClickConfig config = new ClickConfig("joist.web.pages");
    protected ClickServlet clickServlet = new ClickServlet() {
        private static final long serialVersionUID = 1;

        protected ClickConfig createClickConfig() {
            return AbstractClickPageTest.this.config;
        }
    };
    private SessionStub sessionStub = new SessionStub();

    public void setUp() throws Exception {
        super.setUp();
        this.clickServlet.init();
    }

    public RequestBuilder request(String url) throws Exception {
        RequestBuilder request = new RequestBuilder(this.clickServlet, url);
        request.getRequest().setSessionStub(this.sessionStub);
        return request;
    }

}
