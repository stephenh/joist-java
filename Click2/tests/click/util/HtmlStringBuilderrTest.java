package click.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class HtmlStringBuilderrTest extends TestCase {

    private HtmlStringBuilderr sb;

    public void setUp() {
        this.sb = new HtmlStringBuilderr();
    }

    public void testOneNotWrapped() {
        this.sb.line("<p>{}</p>", "bar");
        Assert.assertEquals("<p>bar</p>\n", this.sb.toString());
    }

    public void testOneWrapped() {
        this.sb.line("<p id={}></p>", "bar");
        Assert.assertEquals("<p id=\"bar\"></p>\n", this.sb.toString());
    }

    public void testOneWrappedThenOneNotWrapped() {
        this.sb.line("<p id={}>{}</p>", "bar", "bar");
        Assert.assertEquals("<p id=\"bar\">bar</p>\n", this.sb.toString());
    }

    public void testOneNotWrappedThenOneWrapped() {
        this.sb.line("{}<p id={}></p>", "bar", "bar");
        Assert.assertEquals("bar<p id=\"bar\"></p>\n", this.sb.toString());
    }

}
