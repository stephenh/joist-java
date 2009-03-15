package org.exigencecorp.gen;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.util.Join;

public class GFieldTest extends TestCase {

    public void testOneField() {
        GClass gc = new GClass("foo.bar.Foo");
        gc.getField("id").type(int.class);
        Assert.assertEquals(Join.lines("package foo.bar;",//
            "",
            "public class Foo {",
            "",
            "    private int id;",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOnePublicField() {
        GClass gc = new GClass("foo.bar.Foo");
        gc.getField("id").type(int.class).setPublic();
        Assert.assertEquals(Join.lines("package foo.bar;",//
            "",
            "public class Foo {",
            "",
            "    public int id;",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOneFieldWithDefaultValue() {
        GClass gc = new GClass("foo.bar.Foo");
        gc.getField("id").type(Integer.class).initialValue("null");
        Assert.assertEquals(Join.lines(new Object[] { "package foo.bar;",//
            "",
            "public class Foo {",
            "",
            "    private Integer id = null;",
            "",
            "}",
            "" }), gc.toCode());
    }

    public void testOnePublicFieldWithTypeImported() {
        GClass gc = new GClass("foo.bar.Foo");
        gc.getField("id").type("foo.zaz.Bar<Integer>").setPublic();
        Assert.assertEquals(Join.lines("package foo.bar;",//
            "",
            "import foo.zaz.Bar;",
            "",
            "public class Foo {",
            "",
            "    public Bar<Integer> id;",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOneFieldOneGetter() {
        GClass gc = new GClass("foo.bar.Foo");
        gc.getField("id").type(int.class);
        gc.getMethod("getId").returnType(int.class).body.append("return this.id;");
        Assert.assertEquals(Join.lines(
            "package foo.bar;",
            "",
            "public class Foo {",
            "",
            "    private int id;",
            "",
            "    public int getId() {",
            "        return this.id;",
            "    }",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOneFieldAssignedToAnonymousInnerClass() {
        GClass gc = new GClass("foo.bar.Foo");
        GField foo = gc.getField("foo").type("Shim<Foo>").setStatic().setFinal();

        GClass fooc = foo.initialAnonymousClass();
        fooc.getMethod("getFoo").returnType("Foo").body.append("return null;");

        Assert.assertEquals(Join.lines(
            "package foo.bar;",
            "",
            "public class Foo {",
            "",
            "    private static final Shim<Foo> foo = new Shim<Foo>() {",
            "        public Foo getFoo() {",
            "            return null;",
            "        }",
            "    };",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testTwoFieldsAssignedToAnonymousInnerClass() {
        GClass gc = new GClass("foo.bar.Foo");
        GField foo = gc.getField("foo").type("Shim<Foo>").setStatic().setFinal();
        GClass fooc = foo.initialAnonymousClass();
        fooc.getMethod("getFoo").returnType("Foo").body.append("return null;");

        GField bar = gc.getField("bar").type("Shim<Bar>").setStatic().setFinal();
        GClass barc = bar.initialAnonymousClass();
        barc.getMethod("getBar").returnType("Bar").body.append("return null;");

        Assert.assertEquals(Join.lines(
            "package foo.bar;",
            "",
            "public class Foo {",
            "",
            "    private static final Shim<Foo> foo = new Shim<Foo>() {",
            "        public Foo getFoo() {",
            "            return null;",
            "        }",
            "    };",
            "    private static final Shim<Bar> bar = new Shim<Bar>() {",
            "        public Bar getBar() {",
            "            return null;",
            "        }",
            "    };",
            "",
            "}",
            ""), gc.toCode());
    }

    public void testOneFieldWithGetter() {
        GClass gc = new GClass("foo.bar.Foo");
        gc.getField("id").type(Integer.class).initialValue("null").makeGetter();
        Assert.assertEquals(Join.lines(new Object[] {
            "package foo.bar;",
            "",
            "public class Foo {",
            "",
            "    private Integer id = null;",
            "",
            "    public Integer getId() {",
            "        return this.id;",
            "    }",
            "",
            "}",
            "" }), gc.toCode());
    }

    public void testOneFieldAnnotated() {
        GClass gc = new GClass("foo.bar.Foo");
        gc.getField("id").type(Integer.class).initialValue("null").addAnnotation("@SuppressWarnings");
        Assert.assertEquals(Join.lines(new Object[] {
            "package foo.bar;",
            "",
            "public class Foo {",
            "",
            "    @SuppressWarnings",
            "    private Integer id = null;",
            "",
            "}",
            "" }), gc.toCode());
    }
}
