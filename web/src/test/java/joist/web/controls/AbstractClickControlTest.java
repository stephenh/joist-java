package joist.web.controls;

import java.io.StringWriter;

import joist.web.AbstractClickTest;
import joist.web.Control;
import joist.web.CurrentContext;
import joist.web.WebConfig;
import joist.web.WebContext;
import joist.web.fakedomain.EmployeeToFriendlyStringConverter;
import joist.web.fakedomain.EmployeeToStringConverter;
import joist.web.util.HtmlWriter;

import org.apache.velocity.app.VelocityEngine;

import servletTest.RequestStub;

public abstract class AbstractClickControlTest extends AbstractClickTest {

  protected WebConfig config;
  protected RequestStub request;

  public void setUp() throws Exception {
    super.setUp();
    this.config = new WebConfig("joist.web.pages") {
      @Override
      protected VelocityEngine createVelocityEngine() {
        return testEngine;
      }
    };
    this.config.getUrlConverterRegistry().addConverter(new EmployeeToStringConverter());
    this.config.getTextConverterRegistry().addConverter(new EmployeeToFriendlyStringConverter());
    this.request = new RequestStub();
    CurrentContext.set(new WebContext(null, this.config, this.request, null));
  }

  protected String render(Control c) {
    StringWriter s = new StringWriter();
    c.render(new HtmlWriter(s));
    return s.toString();
  }

}
