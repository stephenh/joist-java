package joist.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Write {

  public static void toFile(String path, String content) {
    Write.toFile(new File(path), content);
  }

  public static void toFile(File file, String content) {
    try {
      file.getParentFile().mkdirs();
      OutputStream out = new FileOutputStream(file);
      out.write(content.getBytes());
      out.flush();
      out.close();
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }

}
