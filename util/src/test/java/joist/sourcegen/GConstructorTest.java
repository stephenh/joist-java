package joist.sourcegen;

import joist.util.Join;

import org.junit.Assert;
import org.junit.Test;

public class GConstructorTest {

  @Test
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

  @Test
  public void testOneConstructorWithArgumentOverload() {
    GClass gc = new GClass("foo.bar.Foo");
    GMethod c = gc.getConstructor(Argument.arg("String", "foo"));
    c.body.line("System.out.println(foo);");
    Assert.assertEquals(Join.lines(
      "package foo.bar;",
      "",
      "public class Foo {",
      "",
      "    public Foo(String foo) {",
      "        System.out.println(foo);",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
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

  @Test
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

  @Test
  public void testStaticConstructor() {
    GClass gc = new GClass("foo.bar.Foo");
    GMethod c = gc.getConstructor().setStatic();
    c.body.line("System.out.println(\"foo\");");
    Assert.assertEquals(Join.lines("package foo.bar;",//
      "",//
      "public class Foo {",//
      "",//
      "    static  {",//
      "        System.out.println(\"foo\");",//
      "    }",//
      "",//
      "}",//
      ""), gc.toCode());
  }

}
