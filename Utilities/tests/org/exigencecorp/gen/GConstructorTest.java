package org.exigencecorp.gen;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.util.Join;

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
            "    private java.lang.Integer id;",
            "",
            "    public Foo() {",
            "        System.out.println(\"foo\");",
            "    }",
            "",
            "    public java.lang.Integer getId() {",
            "        return this.id;",
            "    }",
            "",
            "}",
            ""), gc.toCode());
    }

}
