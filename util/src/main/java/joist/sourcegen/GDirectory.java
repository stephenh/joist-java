package joist.sourcegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import joist.util.Copy;
import joist.util.Log;

public class GDirectory {

  private final File directory;
  private final List<GClass> classes = new ArrayList<GClass>();
  private final List<File> touched = new ArrayList<File>();

  public GDirectory(String directory) {
    this.directory = new File(directory);
  }

  public GClass getClass(String fullClassName) {
    for (GClass gc : this.classes) {
      if (gc.getFullClassName().equals(fullClassName)) {
        return gc;
      }
    }
    GClass gc = new GClass(fullClassName);
    this.classes.add(gc);
    return gc;
  }

  public boolean exists(String fullClassName) {
    return new File(this.directory, fullClassName.replace('.', '/') + ".java").exists();
  }

  public void output() {
    try {
      for (GClass gc : this.classes) {
        File file = this.getFile(gc);
        file.getParentFile().mkdirs();
        Log.debug("Saving {}", file);
        OutputStream out = new FileOutputStream(file);
        out.write(gc.toCode().getBytes());
        out.close();
        this.touched.add(file);
      }
    } catch (IOException io) {
      throw new RuntimeException(io);
    }
  }

  public void pruneIfNotTouched() {
    List<File> directoriesToCheck = Copy.list(this.directory);
    while (directoriesToCheck.size() != 0) {
      File dir = directoriesToCheck.remove(0);
      for (File file : dir.listFiles()) {
        if (file.isDirectory()) {
          directoriesToCheck.add(file);
        } else if (!this.touched.contains(file)) {
          Log.warn("Removing old file {}", file);
          file.delete();
        }
      }
    }
  }

  private File getFile(GClass gc) {
    return new File(this.directory, gc.getFullClassName().replace('.', '/') + ".java");
  }

}
