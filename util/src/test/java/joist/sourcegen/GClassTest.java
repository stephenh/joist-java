package joist.sourcegen;

import java.util.List;

import joist.util.Join;
import junit.framework.Assert;
import junit.framework.TestCase;

public class GClassTest extends TestCase {

  public void testEmptyClass() {
    GClass gc = new GClass("Foo");
    Assert.assertEquals(Join.lines(//
      "public class Foo {",
      "",
      "}",
      ""), gc.toCode());
  }

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

  public void testGenericWithPackages() {
    GClass gc = new GClass("Foo<java.lang.Object>");
    Assert.assertEquals(null, gc.getPackageName());
    Assert.assertEquals("Foo<java.lang.Object>", gc.getFullClassName());
    Assert.assertEquals("Foo", gc.getFullClassNameWithoutGeneric());
  }

  public void testGenericWithPackagesInPackages() {
    GClass gc = new GClass("com.app.Foo<java.lang.Object>");
    Assert.assertEquals("com.app", gc.getPackageName());
    Assert.assertEquals("com.app.Foo<java.lang.Object>", gc.getFullClassName());
    Assert.assertEquals("com.app.Foo", gc.getFullClassNameWithoutGeneric());
  }

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

  public void testFileName() {
    Assert.assertEquals(Join.path("foo", "bar", "Foo.java"), new GClass("foo.bar.Foo").getFileName());
  }

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
}
