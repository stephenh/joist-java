package org.exigencecorp.gen;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.util.Join;

public class GInnerClassTest extends TestCase {

    public void testOneInnerClass() {
        GClass gc = new GClass("Foo");
        GClass bar = gc.getInnerClass("Bar");
        bar.getField("id").type(Integer.class);
        Assert.assertEquals(Join.lines(
            "public class Foo {",
            "",
            "    public static class Bar {",
            "        private java.lang.Integer id;",
            "    }",
            "",
            "}",
            ""), gc.toCode());
    }

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

}
