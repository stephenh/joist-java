package joist.web;

public class RenderException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final String contentType;
  private final byte[] bytes;

  public RenderException(String contentType, byte[] bytes) {
    this.contentType = contentType;
    this.bytes = bytes;
  }

  public String getContentType() {
    return this.contentType;
  }

  public byte[] getBytes() {
    return this.bytes;
  }

}
