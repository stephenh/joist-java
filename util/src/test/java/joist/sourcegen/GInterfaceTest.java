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

    public void testBaseWithGenerics() {
        GClass gc = new GClass("foo.Foo").setInterface().baseClassName("foo.Bar<foo.zazZaz.SomeClass>");
        Assert.assertEquals(Join.lines(//
            "package foo;",
            "",
            "import foo.zazZaz.SomeClass;",
            "",
            "public interface Foo extends Bar<SomeClass> {",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testBaseWithInnerClassIsNotMistookForAPackage() {
        GClass gc = new GClass("foo.Foo").setInterface().baseClassName("Foo.Inner");
        Assert.assertEquals(Join.lines(//
            "package foo;",
            "",
            "public interface Foo extends Foo.Inner {",
            "",
            "}",
            ""), gc.toCode());
    }

}
