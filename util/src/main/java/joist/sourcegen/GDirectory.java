package joist.sourcegen;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import joist.util.Copy;
import joist.util.Interpolate;
import joist.util.Read;
import joist.util.Write;

public class GDirectory {

  private static final Logger log = LoggerFactory.getLogger(GDirectory.class);
  private final File directory;
  private final List<GClass> classes = new ArrayList<GClass>();
  private final List<File> touched = new ArrayList<File>();
  private final Charset charset;

  public GDirectory(String directory) {
    this(directory, Charset.defaultCharset());
  }

  public GDirectory(String directory, Charset charset) {
    this.directory = new File(directory);
    this.charset = charset;
  }

  public GClass getClass(String _fullClassName, Object... args) {
    String fullClassName = Interpolate.string(_fullClassName, args);
    for (GClass gc : this.classes) {
      if (gc.isSameClass(fullClassName)) {
        return gc;
      }
    }
    GClass gc = new GClass(this, fullClassName);
    this.classes.add(gc);
    return gc;
  }

  public boolean exists(String fullClassName) {
    return this.getFile(fullClassName).exists();
  }

  public void output() {
    for (GClass gc : this.classes) {
      String newCode = gc.toCode();

      File file = this.getFile(gc);
      if (file.exists()) {
        String existingCode = Read.fromFile(file, this.charset);
        if (newCode.equals(existingCode)) {
          this.touched.add(file);
          continue;
        }
      }

      file.getParentFile().mkdirs();
      log.info("Saving {}", file);
      Write.toFile(file, newCode, this.charset);
      this.touched.add(file);
    }
  }

  public void pruneIfNotTouched() {
    for (File dir : this.getAllDirectories()) {
      for (File file : dir.listFiles()) {
        if (file.isFile() && !this.touched.contains(file)) {
          log.warn("Removing old file {}", file);
          file.delete();
        }
      }
    }
  }

  public void pruneIfNotTouchedWithinUsedPackages() {
    for (File dir : this.getUsedDirectories()) {
      for (File file : dir.listFiles()) {
        if (file.isFile() && !this.touched.contains(file)) {
          log.warn("Removing old file {}", file);
          file.delete();
        }
      }
    }
  }

  public List<File> getTouched() {
    return this.touched;
  }

  /** Mark {@code file} as touched even if we're not going to explicitly output it. */
  public void markTouched(File file) {
    this.touched.add(file);
  }

  /** Mark {@code className} as touched even if we're not going to explicitly output it. */
  public void markTouched(String fullClassName) {
    this.markTouched(this.getFile(fullClassName));
  }

  /** @returns only directories that we've output classes into, so that we conceptually "own" */
  public List<File> getUsedDirectories() {
    List<File> used = Copy.list();
    for (File dir : this.getAllDirectories()) {
      boolean foundTouchedFileInThisDir = false;
      for (File touched : this.touched) {
        if (touched.getParentFile().equals(dir)) {
          foundTouchedFileInThisDir = true;
          break;
        }
      }
      if (foundTouchedFileInThisDir) {
        used.add(dir);
      }
    }
    return used;
  }

  /** @returns all of the directories starting at {@code this.directory}. */
  public List<File> getAllDirectories() {
    List<File> directories = Copy.list();
    List<File> directoriesToCheck = Copy.list(this.directory);
    while (directoriesToCheck.size() > 0) {
      File dir = directoriesToCheck.remove(0);
      for (File file : dir.listFiles()) {
        if (file.isDirectory()) {
          directories.add(file);
          directoriesToCheck.add(file);
        }
      }
    }
    return directories;
  }

  private File getFile(GClass gc) {
    return this.getFile(gc.getFullName());
  }

  private File getFile(String fullClassName) {
    return new File(this.directory, fullClassName.replace('.', File.separatorChar) + ".java");
  }

}
