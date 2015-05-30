package joist.sourcegen;

import java.util.List;

import joist.util.Join;

import org.junit.Assert;
import org.junit.Test;

public class GClassTest {

  @Test
  public void testEmptyClass() {
    GClass gc = new GClass("Foo");
    Assert.assertEquals(Join.lines(//
      "public class Foo {",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testEmptyClassWithPackage() {
    GClass gc = new GClass("foo.bar.Foo");
    Assert.assertEquals(Join.lines(//
      "package foo.bar;",
      "",
      "public class Foo {",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testOneMethod() {
    GClass gc = new GClass("foo.bar.Foo");
    GMethod hello = gc.getMethod("hello");
    hello.arguments("String foo");
    hello.setBody("return 'Hi' + foo;");
    Assert.assertEquals(
      Join.lines(new Object[] {
        "package foo.bar;",
        "",
        "public class Foo {",
        "",
        "    public void hello(String foo) {",
        "        return \"Hi\" + foo;",
        "    }",
        "",
        "}",
        "" }),
      gc.toCode());
  }

  @Test
  public void testOneMethodWithImports() {
    GClass gc = new GClass("foo.bar.Foo");
    gc.addImports("com.project.Foo");
    gc.addImports("com.project.Foo");
    gc.addImports("com.project.Bar");
    Assert.assertEquals(Join.lines(new Object[] {
      "package foo.bar;",
      "",
      "import com.project.Bar;",
      "import com.project.Foo;",
      "",
      "public class Foo {",
      "",
      "}",
      "" }), gc.toCode());
  }

  @Test
  public void testTwoMethods() {
    GClass gc = new GClass("foo.bar.Foo");

    GMethod hello = gc.getMethod("hello");
    hello.arguments("String foo");
    hello.setBody("return 'Hi' + foo;");

    GMethod goodbye = gc.getMethod("goodbye");
    goodbye.arguments("String foo");
    goodbye.setBody("return 'Bye' + foo;");

    Assert.assertEquals(
      Join.lines(new Object[] {
        "package foo.bar;",
        "",
        "public class Foo {",
        "",
        "    public void hello(String foo) {",
        "        return \"Hi\" + foo;",
        "    }",
        "",
        "    public void goodbye(String foo) {",
        "        return \"Bye\" + foo;",
        "    }",
        "",
        "}",
        "" }),
      gc.toCode());
  }

  @Test
  public void testOneMethodWithThreeLines() {
    GClass gc = new GClass("foo.bar.Foo");
    GMethod hello = gc.getMethod("hello").returnType("int");
    hello.arguments("String foo");
    hello.body.line(0, "int i = 0;");
    hello.body.line(0, "if (foo.equals(\"foo\")) {");
    hello.body.line(1, "i = 1;");
    hello.body.line(0, "}");
    hello.body.line(0, "return i;");
    Assert.assertEquals(
      Join.lines(new Object[] {
        "package foo.bar;",
        "",
        "public class Foo {",
        "",
        "    public int hello(String foo) {",
        "        int i = 0;",
        "        if (foo.equals(\"foo\")) {",
        "            i = 1;",
        "        }",
        "        return i;",
        "    }",
        "",
        "}",
        "" }),
      gc.toCode());
  }

  @Test
  public void testOneMethodWithMultipleArguments() {
    GClass gc = new GClass("foo.bar.Foo");

    GMethod hello = gc.getMethod("hello");
    hello.argument("String", "foo").argument("Integer", "bar");
    hello.setBody("return 'Hi' + foo;");

    Assert.assertEquals(Join.linesWithTickToQuote(
      "package foo.bar;",
      "",
      "public class Foo {",
      "",
      "    public void hello(String foo, Integer bar) {",
      "        return 'Hi' + foo;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testGenericWithPackages() {
    GClass gc = new GClass("Foo<java.lang.Object>");
    Assert.assertEquals(null, gc.getPackageName());
    Assert.assertEquals("Foo", gc.getFullName());
  }

  @Test
  public void testGenericWithPackagesInPackages() {
    GClass gc = new GClass("com.app.Foo<java.lang.Object>");
    Assert.assertEquals("com.app", gc.getPackageName());
    Assert.assertEquals("com.app.Foo", gc.getFullName());
  }

  @Test
  public void testImplements() {
    GClass gc = new GClass("foo.bar.Foo").implementsInterface(List.class);
    Assert.assertEquals(Join.lines("package foo.bar;",//
      "",
      "import java.util.List;",
      "",
      "public class Foo implements List {",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testInterfacesExtendsTwoInterfaces() {
    GClass gc = new GClass("foo.Foo").setInterface();
    gc.baseClassName("Base1");
    gc.implementsInterface("Base2");
    Assert.assertEquals(Join.lines("package foo;",//
      "",
      "public interface Foo extends Base1, Base2 {",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testInterfaceThatIsEmpty() {
    GClass gc = new GClass("foo.Foo").setInterface();
    Assert.assertEquals(Join.lines("package foo;",//
      "",
      "public interface Foo {",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testAnnotated() {
    GClass gc = new GClass("foo.bar.Foo");
    gc.addAnnotation("@SuppressWarnings");
    Assert.assertEquals(Join.lines(new Object[] { "package foo.bar;",//
      "",
      "@SuppressWarnings",
      "public class Foo {",
      "",
      "}",
      "" }), gc.toCode());
  }

  @Test
  public void testGetMethodMatchesArguments() {
    GClass gc = new GClass("Foo");
    GMethod m1 = gc.getMethod("foo(java.lang.String arg)");
    GMethod m2 = gc.getMethod("foo(java.lang.String arg)");
    Assert.assertSame(m1, m2);

    GMethod m3 = gc.getMethod("foo(String arg1)");
    Assert.assertSame(m1, m3);

    GMethod m4 = gc.getMethod("bar()");
    GMethod m5 = gc.getMethod("bar()");
    Assert.assertSame(m4, m5);
  }

  @Test
  public void testGetterSetter() {
    GClass gc = new GClass("foo.bar.Foo");
    gc.addGetterSetter("String", "foo");
    Assert.assertEquals(
      Join.lines(new Object[] {
        "package foo.bar;",
        "",
        "public class Foo {",
        "",
        "    private String foo;",
        "",
        "    public String getFoo() {",
        "        return foo;",
        "    }",
        "",
        "    public void setFoo(String foo) {",
        "        this.foo = foo;",
        "    }",
        "",
        "}",
        "" }),
      gc.toCode());
  }

  @Test
  public void testIndentation() {
    try {
      GSettings.setDefaultIndentation("  ");
      GClass gc = new GClass("foo.bar.Foo");
      gc.addGetterSetter("String", "foo");
      GMethod foo = gc.getMethod("foo");
      foo.body.line("if (true) {");
      foo.body.line("_    if (false) {");
      foo.body.line("_    _   i++;");
      foo.body.line("_    }");
      foo.body.line("}");
      Assert.assertEquals(
        Join.lines(new Object[] {
          "package foo.bar;",
          "",
          "public class Foo {",
          "",
          "  private String foo;",
          "",
          "  public String getFoo() {",
          "    return foo;",
          "  }",
          "",
          "  public void setFoo(String foo) {",
          "    this.foo = foo;",
          "  }",
          "",
          "  public void foo() {",
          "    if (true) {",
          "      if (false) {",
          "        i++;",
          "      }",
          "    }",
          "  }",
          "",
          "}",
          "" }),
        gc.toCode());
    } finally {
      GSettings.setDefaultIndentation("    ");
    }
  }

  @Test
  public void testEquals() {
    GClass gc = new GClass("Foo");
    gc.getField("foo").type("String");
    gc.getField("bar").type("boolean");
    gc.getField("zaz").type("String[]");
    gc.addEquals();
    Assert.assertEquals(Join.lines(
      "public class Foo {",
      "",
      "    private String foo;",
      "    private boolean bar;",
      "    private String[] zaz;",
      "",
      "    @Override",
      "    public boolean equals(Object other) {",
      "        if (other != null && other instanceof Foo) {",
      "            final Foo o = (Foo) other;",
      "            return true",
      "                && ((o.foo == null && this.foo == null) || (o.foo != null && o.foo.equals(this.foo)))",
      "                && o.bar == this.bar",
      "                && java.util.Arrays.deepEquals(o.zaz, this.zaz)",
      "            ;",
      "        }",
      "        return false;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testEqualsSubset() {
    GClass gc = new GClass("Foo");
    gc.getField("foo").type("String");
    gc.getField("bar").type("boolean");
    gc.getField("zaz").type("String[]");
    gc.addEquals("foo", "bar");
    Assert.assertEquals(Join.lines(
      "public class Foo {",
      "",
      "    private String foo;",
      "    private boolean bar;",
      "    private String[] zaz;",
      "",
      "    @Override",
      "    public boolean equals(Object other) {",
      "        if (other != null && other instanceof Foo) {",
      "            final Foo o = (Foo) other;",
      "            return true",
      "                && ((o.foo == null && this.foo == null) || (o.foo != null && o.foo.equals(this.foo)))",
      "                && o.bar == this.bar",
      "            ;",
      "        }",
      "        return false;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testHashCode() {
    GClass gc = new GClass("Foo");
    gc.getField("foo").type("String");
    gc.getField("bar").type("boolean");
    gc.getField("zaz").type("String[]");
    gc.addHashCode();
    Assert.assertEquals(Join.lines(
      "public class Foo {",
      "",
      "    private String foo;",
      "    private boolean bar;",
      "    private String[] zaz;",
      "",
      "    @Override",
      "    public int hashCode() {",
      "        int hashCode = 23;",
      "        hashCode = (hashCode * 37) + getClass().hashCode();",
      "        hashCode = (hashCode * 37) + (foo == null ? 1 : foo.hashCode());",
      "        hashCode = (hashCode * 37) + new Boolean(bar).hashCode();",
      "        hashCode = (hashCode * 37) + java.util.Arrays.deepHashCode(zaz);",
      "        return hashCode;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testHashCodeSubset() {
    GClass gc = new GClass("Foo");
    gc.getField("foo").type("String");
    gc.getField("bar").type("boolean");
    gc.getField("zaz").type("String[]");
    gc.addHashCode("foo", "bar");
    Assert.assertEquals(Join.lines(
      "public class Foo {",
      "",
      "    private String foo;",
      "    private boolean bar;",
      "    private String[] zaz;",
      "",
      "    @Override",
      "    public int hashCode() {",
      "        int hashCode = 23;",
      "        hashCode = (hashCode * 37) + getClass().hashCode();",
      "        hashCode = (hashCode * 37) + (foo == null ? 1 : foo.hashCode());",
      "        hashCode = (hashCode * 37) + new Boolean(bar).hashCode();",
      "        return hashCode;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testToString() {
    GClass gc = new GClass("Foo");
    gc.getField("foo").type("String");
    gc.getField("bar").type("boolean");
    gc.getField("zaz").type("String[]");
    gc.addToString();
    Assert.assertEquals(Join.lines(
      "public class Foo {",
      "",
      "    private String foo;",
      "    private boolean bar;",
      "    private String[] zaz;",
      "",
      "    @Override",
      "    public String toString() {",
      "        return \"Foo[\"",
      "            + foo",
      "            + \", \"",
      "            + bar",
      "            + \", \"",
      "            + java.util.Arrays.toString(zaz)",
      "            + \"]\";",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testToStringSubset() {
    GClass gc = new GClass("Foo");
    gc.getField("foo").type("String");
    gc.getField("bar").type("boolean");
    gc.getField("zaz").type("String[]");
    gc.addToString("foo", "bar");
    Assert.assertEquals(Join.lines(
      "public class Foo {",
      "",
      "    private String foo;",
      "    private boolean bar;",
      "    private String[] zaz;",
      "",
      "    @Override",
      "    public String toString() {",
      "        return \"Foo[\"",
      "            + foo",
      "            + \", \"",
      "            + bar",
      "            + \"]\";",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testToStringWithBaseClass() {
    GDirectory d = new GDirectory("./");

    GClass base = d.getClass("p.Base");
    base.getField("a").type("String");

    GClass foo = d.getClass("p.Foo");
    foo.baseClassName("Base");
    foo.getField("b").type("String");
    // have to pass both a and b for now
    foo.addToString("a", "b");

    Assert.assertEquals(Join.lines(
      "package p;",
      "",
      "public class Foo extends Base {",
      "",
      "    private String b;",
      "",
      "    @Override",
      "    public String toString() {",
      "        return \"Foo[\"",
      "            + a",
      "            + \", \"",
      "            + b",
      "            + \"]\";",
      "    }",
      "",
      "}",
      ""), foo.toCode());
  }

  @Test
  public void testToStringWithGrandparentClass() {
    GDirectory d = new GDirectory("./");

    GClass grand = d.getClass("p.Grand");
    grand.getField("a").type("String");

    GClass parent = d.getClass("p.Parent");
    parent.baseClassName("Grand");
    parent.getField("b").type("String");

    GClass child = d.getClass("p.Child");
    child.baseClassName("Parent");
    child.getField("c").type("String");

    // have to pass both a and b for now
    child.addToString("a", "b", "c");

    Assert.assertEquals(Join.lines(
      "package p;",
      "",
      "public class Child extends Parent {",
      "",
      "    private String c;",
      "",
      "    @Override",
      "    public String toString() {",
      "        return \"Child[\"",
      "            + a",
      "            + \", \"",
      "            + b",
      "            + \", \"",
      "            + c",
      "            + \"]\";",
      "    }",
      "",
      "}",
      ""), child.toCode());
  }
}
