package joist.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Read {

  public static String fromClasspath(String path) {
    return Read.fromInputStream(Read.class.getResourceAsStream(path));
  }

  public static String fromFile(File file) {
    try {
      return Read.fromInputStream(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String fromInputStream(InputStream in) {
    try {
      StringBuilder content = new StringBuilder();
      int read;
      byte[] buffer = new byte[1024];
      while ((read = in.read(buffer)) != -1) {
        content.append(new String(buffer, 0, read, "UTF-8"));
      }
      return content.toString();
    } catch (IOException io) {
      throw new RuntimeException(io);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          // suppress
        }
      }
    }
  }

}
