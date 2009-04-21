package joist.sourcegen;

import joist.sourcegen.GClass;
import joist.sourcegen.GMethod;
import joist.util.Join;
import junit.framework.Assert;
import junit.framework.TestCase;


public class GConstructorTest extends TestCase {

    public void testOneConstructor() {
        GClass gc = new GClass("foo.bar.Foo");
        GMethod c = gc.getConstructor();
        c.body.line("System.out.println(\"foo\");");
        Assert.assertEquals(Join.lines(
            "package foo.bar;",
            "",
            "public class Foo {",
            "",
            "    public Foo() {",
            "        System.out.println(\"foo\");",
            "    }",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOneFieldOneConstructorAndOneMethod() {
        GClass gc = new GClass("foo.bar.Foo");
        GMethod c = gc.getConstructor();
        c.body.line("System.out.println(\"foo\");");

        gc.getField("id").type(Integer.class);
        gc.getMethod("getId").returnType(Integer.class).body.append("return this.id;");

        Assert.assertEquals(Join.lines(
            "package foo.bar;",
            "",
            "public class Foo {",
            "",
            "    private Integer id;",
            "",
            "    public Foo() {",
            "        System.out.println(\"foo\");",
            "    }",
            "",
            "    public Integer getId() {",
            "        return this.id;",
            "    }",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOneConstructorWithGenerics() {
        GClass gc = new GClass("foo.bar.Foo<T>");
        GMethod c = gc.getConstructor();
        c.body.line("System.out.println(\"foo\");");
        Assert.assertEquals(Join.lines(
            "package foo.bar;",
            "",
            "public class Foo<T> {",
            "",
            "    public Foo() {",
            "        System.out.println(\"foo\");",
            "    }",
            "",
            "}",
            ""), gc.toCode());
    }

}
