package joist.web.exceptions;

import java.io.IOException;

public class IoException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public IoException(IOException io) {
    super(io);
  }

  public IOException getCause() {
    return (IOException) super.getCause();
  }

}
