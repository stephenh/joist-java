package servletTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ResponseStub implements HttpServletResponse {

  private StringWriter output = new StringWriter();
  private PrintWriter writer = new PrintWriter(this.output);
  private String redirect;

  public ResponseStub() {
  }

  public void addCookie(Cookie arg0) {
  }

  public void addDateHeader(String arg0, long arg1) {
  }

  public void addHeader(String arg0, String arg1) {
  }

  public void addIntHeader(String arg0, int arg1) {
  }

  public boolean containsHeader(String arg0) {
    return false;
  }

  public String encodeRedirectURL(String arg0) {
    return null;
  }

  public String encodeRedirectUrl(String arg0) {
    return null;
  }

  public String encodeURL(String arg0) {
    return null;
  }

  public String encodeUrl(String arg0) {
    return null;
  }

  public void sendError(int arg0) throws IOException {
  }

  public void sendError(int arg0, String arg1) throws IOException {
  }

  public String getRedirect() {
    return this.redirect;
  }

  public void sendRedirect(String arg0) throws IOException {
    this.redirect = arg0;
  }

  public void setDateHeader(String arg0, long arg1) {
  }

  public void setHeader(String arg0, String arg1) {
  }

  public void setIntHeader(String arg0, int arg1) {
  }

  public void setStatus(int arg0) {
  }

  public void setStatus(int arg0, String arg1) {
  }

  public void flushBuffer() throws IOException {
  }

  public int getBufferSize() {
    return 0;
  }

  public String getCharacterEncoding() {
    return null;
  }

  public String getContentType() {
    return null;
  }

  public Locale getLocale() {
    return null;
  }

  public ServletOutputStream getOutputStream() throws IOException {
    return null;
  }

  public PrintWriter getWriter() throws IOException {
    return this.writer;
  }

  public String getWriterOutput() {
    return this.output.toString();
  }

  public boolean isCommitted() {
    return false;
  }

  public void reset() {

  }

  public void resetBuffer() {

  }

  public void setBufferSize(int arg0) {
  }

  public void setCharacterEncoding(String arg0) {
  }

  public void setContentLength(int arg0) {
  }

  public void setContentType(String arg0) {
  }

  public void setLocale(Locale arg0) {
  }

}
