package joist.sourcegen;

import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

import joist.util.Copy;
import joist.util.Join;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GDirectoryTest {

  private String directory = "./build/GDirectoryTest";
  private File foo = new File(this.directory + "/org/exigencecorp/Foo.java");

  @Before
  public void setUp() {
    if (this.foo.exists()) {
      this.foo.delete();
    }
    new File(this.directory).mkdir();
  }

  @Test
  public void testOutput() throws Exception {
    Assert.assertFalse(this.foo.exists());

    GDirectory bin = new GDirectory(this.directory);
    GClass gc = bin.getClass("org.exigencecorp.Foo");
    gc.getMethod("foo").setBody("int i = 0;");
    bin.output();

    Assert.assertEquals(Join.lines(//
      "package org.exigencecorp;",
      "",
      "public class Foo {",
      "",
      "    public void foo() {",
      "        int i = 0;",
      "    }",
      "",
      "}",
      ""), this.read(this.foo));
  }

  @Test
  public void testMarkTouched() {
    GDirectory bin = new GDirectory(this.directory);
    bin.getClass("org.exigencecorp.Foo");
    bin.output();
    Assert.assertEquals(Copy.list(new File(this.directory + "/org/exigencecorp/Foo.java")), bin.getTouched());
    Assert.assertEquals(Copy.list(new File(this.directory + "/org/exigencecorp")), bin.getUsedDirectories());
    Assert.assertEquals(Copy.list(//
      new File(this.directory + "/org"),
      new File(this.directory + "/org/exigencecorp")), bin.getAllDirectories());
  }

  private String read(File file) throws Exception {
    StringWriter writer = new StringWriter();
    FileReader reader = new FileReader(file);
    char[] buffer = new char[1024 * 4];
    int n = 0;
    while (-1 != (n = reader.read(buffer))) {
      writer.write(buffer, 0, n);
    }
    reader.close();
    return writer.toString();
  }

}
