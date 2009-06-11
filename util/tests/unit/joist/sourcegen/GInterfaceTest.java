package joist.sourcegen;

import joist.util.Join;
import junit.framework.Assert;
import junit.framework.TestCase;

public class GInterfaceTest extends TestCase {

    public void testEmptyEnum() {
        GClass gc = new GClass("foo.bar.Foo").setInterface();
        Assert.assertEquals(Join.lines(//
            "package foo.bar;",
            "",
            "public interface Foo {",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOneMethodEnum() {
        GClass gc = new GClass("foo.bar.Foo").setInterface();
        gc.getMethod("foo").arguments("Bar bar");
        Assert.assertEquals(Join.lines(//
            "package foo.bar;",
            "",
            "public interface Foo {",
            "",
            "    public void foo(Bar bar);",
            "",
            "}",
            ""), gc.toCode());
    }

}
