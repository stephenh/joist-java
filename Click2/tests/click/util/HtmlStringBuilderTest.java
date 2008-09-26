package click.util;

import junit.framework.Assert;
import junit.framework.TestCase;

public class HtmlStringBuilderTest extends TestCase {

    private HtmlStringBuilder sb;

    public void setUp() {
        this.sb = new HtmlStringBuilder();
    }

    public void testOneNotWrapped() {
        this.sb.appendln("<p>{}</p>", "bar");
        Assert.assertEquals("<p>bar</p>\n", this.sb.toString());
    }

    public void testOneWrapped() {
        this.sb.appendln("<p id={}></p>", "bar");
        Assert.assertEquals("<p id=\"bar\"></p>\n", this.sb.toString());
    }

    public void testOneWrappedThenOneNotWrapped() {
        this.sb.appendln("<p id={}>{}</p>", "bar", "bar");
        Assert.assertEquals("<p id=\"bar\">bar</p>\n", this.sb.toString());
    }

    public void testOneNotWrappedThenOneWrapped() {
        this.sb.appendln("{}<p id={}></p>", "bar", "bar");
        Assert.assertEquals("bar<p id=\"bar\"></p>\n", this.sb.toString());
    }

}
