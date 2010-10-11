package joist.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import joist.util.Reflection;
import joist.web.exceptions.IoException;

public abstract class WebServlet extends HttpServlet {

  private static final long serialVersionUID = 1;
  protected ServletConfig servletConfig;
  protected WebConfig clickConfig;

  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
    super.init(servletConfig);
    this.servletConfig = servletConfig;
    this.clickConfig = this.createWebConfig();
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    WebContext context = new WebContext(this.servletConfig, this.clickConfig, request, response);
    CurrentContext.set(context);
    try {
      Page page = this.getPage(context);
      context.setPage(page);
      page.getProcessor().process(page);
    } catch (IoException io) {
      throw io.getCause();
    }
  }

  protected Page getPage(WebContext context) {
    // Get the path, e.g. /page.htm (without the webapp context)
    String path = context.getRequest().getServletPath();
    String className = context.getWebConfig().getPageResolver().getPageFromPath(path);
    return (Page) Reflection.newInstance(className);
  }

  /** Should be implemented by each app to create its configuration. */
  protected abstract WebConfig createWebConfig();

}
