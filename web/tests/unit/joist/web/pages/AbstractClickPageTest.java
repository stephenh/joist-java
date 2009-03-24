package joist.web.pages;

import joist.web.AbstractClickTest;
import joist.web.ClickConfig;
import joist.web.ClickServlet;

import org.apache.velocity.app.VelocityEngine;

import servletTest.RequestBuilder;
import servletTest.SessionStub;

public abstract class AbstractClickPageTest extends AbstractClickTest {

    protected ClickConfig config;
    protected ClickServlet clickServlet;
    private SessionStub sessionStub = new SessionStub();

    public void setUp() throws Exception {
        super.setUp();
        this.clickServlet = new ClickServlet() {
            private static final long serialVersionUID = 1;

            protected ClickConfig createClickConfig() {
                return AbstractClickPageTest.this.config;
            }
        };
        this.config = new ClickConfig("joist.web.pages") {
            @Override
            protected VelocityEngine createVelocityEngine() {
                return testEngine;
            }
        };
        this.clickServlet.init();
    }

    public RequestBuilder request(String url) throws Exception {
        RequestBuilder request = new RequestBuilder(this.clickServlet, url);
        request.getRequest().setSessionStub(this.sessionStub);
        return request;
    }

}
