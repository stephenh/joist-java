package joist.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Write {

  public static File classpathResourceToTempFile(String resource) {
    // cheat and use File.getName to parse the resource
    try {
      File destination = File.createTempFile(new File(resource).getName() + ".", "");
      destination.deleteOnExit();
      InputStream in = Write.class.getResourceAsStream(resource);
      Write.toFile(destination, in);
      in.close();
      return destination;
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }

  public static void toFile(String path, String content, Charset charset) {
    Write.toFile(new File(path), content, charset);
  }

  public static void toFile(File file, String content, Charset charset) {
    file.getParentFile().mkdirs();
    try {
      OutputStream out = new FileOutputStream(file);
      out.write(content.getBytes(charset));
      out.flush();
      out.close();
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }

  public static void toFile(File file, InputStream content) {
    file.getParentFile().mkdirs();
    try {
      OutputStream out = new FileOutputStream(file);
      byte[] buffer = new byte[1024];
      int len;
      while ((len = content.read(buffer)) != -1) {
        out.write(buffer, 0, len);
      }
      out.flush();
      out.close();
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }
}
