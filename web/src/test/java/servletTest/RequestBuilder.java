package servletTest;

import javax.servlet.http.HttpServlet;

public class RequestBuilder {

  private HttpServlet servlet;
  private RequestStub request = new RequestStub();

  /**
   * @param servlet the servlet to test
   * @param uri uri of page to test, e.g. "/webapp/page.htm"
   */
  public RequestBuilder(HttpServlet servlet, String uri) throws Exception {
    this.servlet = servlet;
    this.servlet.init(null);
    // For now assume requestURI = servletPath which is only the case if context = /
    this.request.setRequestURI(uri);
    this.request.setServletPath(uri);
  }

  public ResponseParser get() throws Exception {
    ResponseStub response = new ResponseStub();
    this.request.setMethod("GET");
    this.servlet.service(this.request, response);
    return new ResponseParser(response);
  }

  public ResponseParser post() throws Exception {
    ResponseStub response = new ResponseStub();
    this.request.setMethod("POST");
    this.servlet.service(this.request, response);
    return new ResponseParser(response);
  }

  /** Alias for <code>get().body()</code>. */
  public String getBody() throws Exception {
    return this.get().getBody();
  }

  public String getRedirect() throws Exception {
    return this.get().getRedirect();
  }

  /** Alias for <code>post().body()</code>. */
  public String postBody() throws Exception {
    return this.post().getBody();
  }

  public RequestBuilder set(String name, String value) {
    this.request.setParameter(name, new String[] { value });
    return this;
  }

  public RequestBuilder contextPath(String path) {
    this.request.setContextPath(path);
    return this;
  }

  public HttpServlet getServlet() {
    return this.servlet;
  }

  public RequestStub getRequest() {
    return this.request;
  }

}
