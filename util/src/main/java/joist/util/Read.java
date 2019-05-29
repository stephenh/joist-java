package joist.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class Read {

  public static String fromClasspath(String path, Charset charset) {
    try {
      InputStream in = Read.class.getResourceAsStream(path);
      String content = Read.fromInputStream(in, charset);
      in.close();
      return content;
    } catch (IOException e) {
      throw new RuntimeException(e); // for in.close(), which shouldn't happen
    }
  }

  public static String fromFile(String path, Charset charset) {
    return fromFile(new File(path), charset);
  }

  public static String fromFile(File file, Charset charset) {
    try {
      return Read.fromInputStream(new FileInputStream(file), charset);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static String fromInputStream(InputStream in, Charset charset) {
    try {
      StringBuilder content = new StringBuilder();
      int read;
      byte[] buffer = new byte[1024];
      while ((read = in.read(buffer)) != -1) {
        content.append(new String(buffer, 0, read, charset));
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
