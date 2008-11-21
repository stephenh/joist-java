package org.exigencecorp.gen;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.exigencecorp.util.Join;

public class GMethodTest extends TestCase {

    public void testOverloadedMethods() {
        GClass gc = new GClass("Foo");
        gc.getMethod("method(String one)");
        gc.getMethod("method(Integer two)");
        Assert.assertEquals(Join.lines(//
            "public class Foo {",
            "",
            "    public void method(String one) {",
            "    }",
            "",
            "    public void method(Integer two) {",
            "    }",
            "",
            "}",
            ""), gc.toCode());
    }

}
