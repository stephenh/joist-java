package joist.util;

import java.io.IOException;
import java.io.InputStream;

public class Read {

  public static String fromClasspath(String path) {
    String content = "";
    try {
      int read;
      byte[] buffer = new byte[1024];
      InputStream in = Read.class.getResourceAsStream(path);
      while ((read = in.read(buffer)) != -1) {
        content += new String(buffer, 0, read, "UTF-8");
      }
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
    return content;
  }

}
