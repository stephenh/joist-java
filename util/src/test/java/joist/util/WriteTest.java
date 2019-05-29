package joist.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class WriteTest {

  @Test
  public void testClasspathResourceToFile() {
    File d = Write.classpathResourceToTempFile("/joist/util/WriteTest.txt");
    System.out.println(d);
    String contents = Read.fromFile(d, Charset.defaultCharset());
    Assert.assertEquals("foo", contents);
  }

  @Test
  public void testClasspathResourceToFileWithDefaultEncoding() throws IOException {
    File tmpFile = File.createTempFile("junit", ".txt");
    Write.toFile(tmpFile, "abcåäö", Charset.defaultCharset());
    String contents = Read.fromFile(tmpFile, Charset.defaultCharset());
    Assert.assertEquals("abcåäö", contents);
  }

  @Test
  public void testClasspathResourceToFileWithUtf8Encoding() throws IOException {
    File tmpFile = File.createTempFile("junit", ".txt");
    Write.toFile(tmpFile, "abcåäö", StandardCharsets.UTF_8);
    String contents = Read.fromFile(tmpFile, StandardCharsets.UTF_8);
    Assert.assertEquals("abcåäö", contents);
  }

}
