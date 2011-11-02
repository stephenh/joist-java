package joist.sourcegen;

import joist.util.Join;

import org.junit.Assert;
import org.junit.Test;

public class GClassStaticInitializerTest {

  @Test
  public void testEmptyClass() {
    GClass gc = new GClass("Foo");
    gc.staticInitializer.append("int i = 0;");
    Assert.assertEquals(Join.lines(//
      "public class Foo {",
      "",
      "    static {",
      "        int i = 0;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testOneMethod() {
    GClass gc = new GClass("foo.bar.Foo");
    gc.staticInitializer.append("int i = 0;");
    GMethod hello = gc.getMethod("hello");
    hello.arguments("String foo");
    hello.setBody("return 'Hi' + foo;");
    Assert.assertEquals(
      Join.lines(new Object[] {
        "package foo.bar;",
        "",
        "public class Foo {",
        "",
        "    static {",
        "        int i = 0;",
        "    }",
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
  public void testOneStaticField() {
    GClass gc = new GClass("foo.bar.Foo");
    gc.staticInitializer.append("i = 0;");
    gc.getField("i").type(int.class).setStatic().setFinal();
    Assert.assertEquals(
      Join.lines(new Object[] {
        "package foo.bar;",
        "",
        "public class Foo {",
        "",
        "    private static final int i;",
        "",
        "    static {",
        "        i = 0;",
        "    }",
        "",
        "}",
        "" }),
      gc.toCode());
  }

}
