package joist.util;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class WriteTest {

  @Test
  public void testClasspathResourceToFile() {
    File d = Write.classpathResourceToTempFile("/joist/util/WriteTest.txt");
    System.out.println(d);
    String contents = Read.fromFile(d);
    Assert.assertEquals("foo", contents);
  }

}
