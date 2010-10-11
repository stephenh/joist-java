package joist.web.pages;

import joist.web.AbstractClickTest;
import joist.web.WebConfig;
import joist.web.WebServlet;
import joist.web.fakedomain.EmployeeToFriendlyStringConverter;
import joist.web.fakedomain.EmployeeToStringConverter;

import org.apache.velocity.app.VelocityEngine;

import servletTest.RequestBuilder;
import servletTest.SessionStub;

public abstract class AbstractClickPageTest extends AbstractClickTest {

  protected WebConfig config;
  protected WebServlet clickServlet;
  private SessionStub sessionStub = new SessionStub();

  public void setUp() throws Exception {
    super.setUp();
    this.clickServlet = new WebServlet() {
      private static final long serialVersionUID = 1;

      protected WebConfig createWebConfig() {
        return AbstractClickPageTest.this.config;
      }
    };
    this.config = new WebConfig("joist.web.pages") {
      @Override
      protected VelocityEngine createVelocityEngine() {
        return testEngine;
      }
    };
    this.config.getUrlConverterRegistry().addConverter(new EmployeeToStringConverter());
    this.config.getTextConverterRegistry().addConverter(new EmployeeToFriendlyStringConverter());
    this.clickServlet.init();
  }

  public RequestBuilder request(String url) throws Exception {
    RequestBuilder request = new RequestBuilder(this.clickServlet, url);
    request.getRequest().setSessionStub(this.sessionStub);
    return request;
  }

}
