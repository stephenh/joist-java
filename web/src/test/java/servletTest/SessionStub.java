package servletTest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class SessionStub implements HttpSession {

  private Map<String, Object> values = new HashMap<String, Object>();

  @Override
  public Object getAttribute(String arg0) {
    return this.values.get(arg0);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Enumeration getAttributeNames() {
    return null;
  }

  @Override
  public long getCreationTime() {
    return 0;
  }

  @Override
  public String getId() {
    return null;
  }

  @Override
  public long getLastAccessedTime() {
    return 0;
  }

  @Override
  public int getMaxInactiveInterval() {
    return 0;
  }

  @Override
  public ServletContext getServletContext() {
    return null;
  }

  @Override
  public HttpSessionContext getSessionContext() {
    return null;
  }

  @Override
  public Object getValue(String arg0) {
    return null;
  }

  @Override
  public String[] getValueNames() {
    return null;
  }

  @Override
  public void invalidate() {
    this.values.clear();
  }

  @Override
  public boolean isNew() {
    return false;
  }

  @Override
  public void putValue(String arg0, Object arg1) {
    this.values.put(arg0, arg1);
  }

  @Override
  public void removeAttribute(String arg0) {
    this.values.remove(arg0);
  }

  @Override
  public void removeValue(String arg0) {
  }

  @Override
  public void setAttribute(String arg0, Object arg1) {
    this.values.put(arg0, arg1);
  }

  @Override
  public void setMaxInactiveInterval(int arg0) {
  }

}
