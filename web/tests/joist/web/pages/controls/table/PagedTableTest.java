package joist.web.pages.controls.table;

import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

public class PagedTableTest extends AbstractClickPageTest {

    public void testPage1() throws Exception {
        String body = this.request("/controls/table/pagedTable.htm").getBody();
        this.assertRows(body, 1, 10);
    }

    public void testPage2() throws Exception {
        String body = this.request("/controls/table/pagedTable.htm").set("page", "2").getBody();
        this.assertRows(body, 11, 20);
    }

    public void testPage10() throws Exception {
        String body = this.request("/controls/table/pagedTable.htm").set("page", "10").getBody();
        this.assertRows(body, 91, 100);
    }

    public void testPage11GetsDownGradedTo10() throws Exception {
        String body = this.request("/controls/table/pagedTable.htm").set("page", "11").getBody();
        this.assertRows(body, 91, 100);
    }

    public void testPage1With95() throws Exception {
        String body = this.request("/controls/table/pagedTable.htm").set("page", "1").set("rows", "95").getBody();
        this.assertRows(body, 1, 95);
    }

    public void testPage2With95() throws Exception {
        String body = this.request("/controls/table/pagedTable.htm").set("page", "2").set("rows", "95").getBody();
        this.assertRows(body, 96, 100);
    }

    public void testPage1With105() throws Exception {
        String body = this.request("/controls/table/pagedTable.htm").set("page", "1").set("rows", "105").getBody();
        this.assertRows(body, 1, 100);
    }

    private void assertRows(String body, int start, int end) {
        Assert.assertFalse("was not supposed to have " + (start - 1), body.contains("foo" + (start - 1)));
        for (int i = start; i <= end; i++) {
            Assert.assertTrue("was supposed to have " + i, body.contains("foo" + i));
        }
        Assert.assertFalse("was not supposed to have " + (end + 1), body.contains("foo" + (end + 1)));
    }
}
