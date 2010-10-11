package joist.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import joist.util.Model;

public class WebContext {

  private final ServletConfig servletConfig;
  private final WebConfig webConfig;
  private final HttpServletRequest request;
  private final HttpServletResponse response;
  private final Model model = new Model();
  private final List<Control> allControls = new ArrayList<Control>();
  private Page page;

  public WebContext(ServletConfig servletConfig, WebConfig clickConfig, HttpServletRequest request, HttpServletResponse response) {
    this.servletConfig = servletConfig;
    this.webConfig = clickConfig;
    this.request = request;
    this.response = response;
  }

  public void addControl(Control control) {
    this.allControls.add(control);
  }

  public Page getPage() {
    return this.page;
  }

  public void setPage(Page page) {
    if (this.page != null) {
      throw new RuntimeException("No changing the page after its been set--forwarding is evil, redirect instead.");
    }
    this.page = page;
  }

  public boolean isPost() {
    return this.getRequest().getMethod().equals("POST");
  }

  public HttpServletRequest getRequest() {
    return this.request;
  }

  public HttpServletResponse getResponse() {
    return this.response;
  }

  public ServletConfig getServletConfig() {
    return this.servletConfig;
  }

  public WebConfig getWebConfig() {
    return this.webConfig;
  }

  @SuppressWarnings("unchecked")
  public Map<String, String[]> getParameters() {
    return this.getRequest().getParameterMap();
  }

  public Flash getFlash() {
    Flash flash = (Flash) this.getRequest().getSession().getAttribute("_flash");
    if (flash == null) {
      flash = new Flash();
      this.getRequest().getSession().setAttribute("_flash", flash);
    }
    return flash;
  }

  public Model getModel() {
    return this.model;
  }

  public List<Control> getAllControls() {
    return this.allControls;
  }

}
