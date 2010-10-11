package servletTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RequestStub implements HttpServletRequest {

  private String method;
  private String requestURI; // --> /webapp/foo.servlet
  private String servletPath; // --> /foo.servlet
  private SessionStub session = new SessionStub();
  private Map<String, String[]> parameters = new HashMap<String, String[]>();
  private String contextPath = "";

  public RequestStub() {
  }

  public String getAuthType() {
    return null;
  }

  public String getContextPath() {
    return this.contextPath;
  }

  public void setContextPath(String contextPath) {
    this.contextPath = contextPath;
  }

  public Cookie[] getCookies() {
    return null;
  }

  public long getDateHeader(String arg0) {
    return 0;
  }

  public String getHeader(String arg0) {
    return null;
  }

  public Enumeration<String> getHeaderNames() {
    return null;
  }

  public Enumeration<String> getHeaders(String arg0) {
    return null;
  }

  public int getIntHeader(String arg0) {
    return 0;
  }

  public String getMethod() {
    return this.method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getPathInfo() {
    return null;
  }

  public String getPathTranslated() {
    return null;
  }

  public String getQueryString() {
    return null;
  }

  public String getRemoteUser() {
    return null;
  }

  public String getRequestURI() {
    return this.requestURI;
  }

  public void setRequestURI(String requestURI) {
    this.requestURI = requestURI;
  }

  public StringBuffer getRequestURL() {
    return null;
  }

  public String getRequestedSessionId() {
    return null;
  }

  public String getServletPath() {
    return this.servletPath;
  }

  public HttpSession getSession() {
    return this.session;
  }

  public HttpSession getSession(boolean arg0) {
    return this.session;
  }

  public Principal getUserPrincipal() {
    return null;
  }

  public boolean isRequestedSessionIdFromCookie() {
    return false;
  }

  public boolean isRequestedSessionIdFromURL() {
    return false;
  }

  public boolean isRequestedSessionIdFromUrl() {
    return false;
  }

  public boolean isRequestedSessionIdValid() {
    return false;
  }

  public boolean isUserInRole(String arg0) {
    return false;
  }

  public Object getAttribute(String arg0) {
    return null;
  }

  public Enumeration<String> getAttributeNames() {
    return null;
  }

  public String getCharacterEncoding() {
    return null;
  }

  public int getContentLength() {
    return 0;
  }

  public String getContentType() {
    return null;
  }

  public ServletInputStream getInputStream() throws IOException {
    return null;
  }

  public String getLocalAddr() {
    return null;
  }

  public String getLocalName() {
    return null;
  }

  public int getLocalPort() {
    return 0;
  }

  public Locale getLocale() {
    return null;
  }

  public Enumeration<Locale> getLocales() {
    return null;
  }

  public void setParameter(String key, String... values) {
    this.parameters.put(key, values);
  }

  public String getParameter(String key) {
    String[] values = this.parameters.get(key);
    if (values == null || values.length == 0) {
      return null;
    }
    return values[0];
  }

  public Map<String, String[]> getParameterMap() {
    return this.parameters;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Enumeration<String> getParameterNames() {
    return new Vector(this.parameters.keySet()).elements();
  }

  public String[] getParameterValues(String key) {
    return this.parameters.get(key);
  }

  public String getProtocol() {
    return null;
  }

  public BufferedReader getReader() throws IOException {
    return null;
  }

  public String getRealPath(String arg0) {
    return null;
  }

  public String getRemoteAddr() {
    return null;
  }

  public String getRemoteHost() {
    return null;
  }

  public int getRemotePort() {
    return 0;
  }

  public RequestDispatcher getRequestDispatcher(String arg0) {
    return null;
  }

  public String getScheme() {
    return null;
  }

  public String getServerName() {
    return null;
  }

  public int getServerPort() {
    return 0;
  }

  public boolean isSecure() {
    return false;
  }

  public void removeAttribute(String arg0) {
  }

  public void setAttribute(String arg0, Object arg1) {
  }

  public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
  }

  public void setServletPath(String servletPath) {
    this.servletPath = servletPath;
  }

  public SessionStub getSessionStub() {
    return this.session;
  }

  public void setSessionStub(SessionStub session) {
    this.session = session;
  }

}
