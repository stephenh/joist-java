package joist.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Write {

  public static void toFile(String path, String content) {
    try {
      new File(path).getParentFile().mkdirs();
      OutputStream out = new FileOutputStream(path);
      out.write(content.getBytes());
      out.flush();
      out.close();
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }

}
