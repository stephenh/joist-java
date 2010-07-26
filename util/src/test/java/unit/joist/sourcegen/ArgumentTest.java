package joist.sourcegen;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public class ArgumentTest extends TestCase {

    public void testSplit() {
        final String[] e1 = { "Foo f", "Bar f" };
        final String[] i1 = { "Foo f", "Bar f" };
        this.assertSplit(e1, i1);

        final String[] e2 = { "Foo f", "Bar f" };
        final String[] i2 = { "Foo f, Bar f" };
        this.assertSplit(e2, i2);

        final String[] e3 = { "Foo<String, String> f", "Bar<Integer, Integer> b" };
        final String[] i3 = { "Foo<String, String> f, Bar<Integer, Integer> b" };
        this.assertSplit(e3, i3);

        final String[] e4 = { "Foo<String,String> f", "Bar<Integer,Integer> b" };
        final String[] i4 = { "Foo<String,String> f,Bar<Integer,Integer> b" };
        this.assertSplit(e4, i4);
    }

    private void assertSplit(String[] expected, String[] inputTypeAndNames) {
        List<Argument> actual = Argument.split(inputTypeAndNames);
        Assert.assertEquals(expected.length, actual.size());
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], actual.get(i).toString());
        }
    }
}
