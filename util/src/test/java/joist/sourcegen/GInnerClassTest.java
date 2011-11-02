package joist.sourcegen;

import joist.util.Join;

import org.junit.Assert;
import org.junit.Test;

public class GInnerClassTest {

  @Test
  public void testOneInnerClass() {
    GClass gc = new GClass("Foo");
    GClass bar = gc.getInnerClass("Bar");
    bar.getField("id").type(Integer.class);
    Assert.assertEquals(Join.lines(//
      "public class Foo {",
      "",
      "    public static class Bar {",
      "        private Integer id;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testOneNonStaticInnerClass() {
    GClass gc = new GClass("Foo");
    GClass bar = gc.getInnerClass("Bar").notStatic();
    bar.getField("id").type(Integer.class);
    Assert.assertEquals(Join.lines(//
      "public class Foo {",
      "",
      "    public class Bar {",
      "        private Integer id;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testOneTwoInnerClasses() {
    GClass gc = new GClass("Foo");

    GClass bar = gc.getInnerClass("Bar");
    bar.getField("id").type(int.class);

    GClass zaz = gc.getInnerClass("Zaz");
    zaz.getField("id").type(int.class);

    Assert.assertEquals(Join.lines(
      "public class Foo {",
      "",
      "    public static class Bar {",
      "        private int id;",
      "    }",
      "",
      "    public static class Zaz {",
      "        private int id;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testOneInnerClassWithImports() {
    GClass gc = new GClass("Foo");
    GClass bar = gc.getInnerClass("Bar");
    bar.addImports("other.package.Zaz");
    bar.getField("id").type(Integer.class);
    Assert.assertEquals(Join.lines(//
      "import other.package.Zaz;",
      "",
      "public class Foo {",
      "",
      "    public static class Bar {",
      "        private Integer id;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

  @Test
  public void testOneInnerClassWithImportsInSamePackage() {
    GClass gc = new GClass("foo.Foo");
    GClass bar = gc.getInnerClass("Bar");
    bar.addImports("foo.Zaz");
    bar.getField("id").type(Integer.class);
    Assert.assertEquals(Join.lines(//
      "package foo;",
      "",
      "public class Foo {",
      "",
      "    public static class Bar {",
      "        private Integer id;",
      "    }",
      "",
      "}",
      ""), gc.toCode());
  }

}
