package click.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ClassNameTest extends TestCase {

    public void testRootDirectory() {
        Assert.assertEquals("Abc", ClassName.fromUri("/abc.htm"));
    }

    public void testTwoSubDirectories() {
        Assert.assertEquals("a.b.C", ClassName.fromUri("/a/b/c.htm"));
    }
}
