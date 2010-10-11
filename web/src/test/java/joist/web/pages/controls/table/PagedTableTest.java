package joist.web.pages.controls.table;

import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

public class PagedTableTest extends AbstractClickPageTest {

  public void testPage1() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").getBody();
    this.assertRows(body, 1, 10);
    this.assertNoPrevious(body);
    this.assertNext(body, 2);
  }

  public void testPage2() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").set("page", "2").getBody();
    this.assertRows(body, 11, 20);
    this.assertPrevious(body, 1);
    this.assertNext(body, 3);
  }

  public void testPage10() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").set("page", "10").getBody();
    this.assertRows(body, 91, 100);
    this.assertPrevious(body, 9);
    this.assertNoNext(body);
  }

  public void testPage11GetsDownGradedTo10() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").set("page", "11").getBody();
    this.assertRows(body, 91, 100);
    this.assertPrevious(body, 9);
    this.assertNoNext(body);
  }

  public void testPage1With95() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").set("page", "1").set("rows", "95").getBody();
    this.assertRows(body, 1, 95);
    this.assertNoPrevious(body);
    this.assertNextWithRows(body, 2, 95);
  }

  public void testPage2With95() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").set("page", "2").set("rows", "95").getBody();
    this.assertRows(body, 96, 100);
    this.assertPreviousWithRows(body, 1, 95);
    this.assertNoNext(body);
  }

  public void testPage1With105() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").set("page", "1").set("rows", "105").getBody();
    this.assertRows(body, 1, 100);
    this.assertNoPrevious(body);
    this.assertNoNext(body);
  }

  public void testPage1WithNoEntries() throws Exception {
    String body = this.request("/controls/table/pagedTable.htm").set("emptyTable", "true").getBody();
    this.assertNoPrevious(body);
    this.assertNoNext(body);
  }

  private void assertRows(String body, int start, int end) {
    Assert.assertFalse("was not supposed to have " + (start - 1), body.contains("foo" + (start - 1)));
    for (int i = start; i <= end; i++) {
      Assert.assertTrue("was supposed to have " + i, body.contains("foo" + i));
    }
    Assert.assertFalse("was not supposed to have " + (end + 1), body.contains("foo" + (end + 1)));
  }

  private void assertNoPrevious(String body) {
    Assert.assertFalse(body.contains("table.previous"));
  }

  private void assertNoNext(String body) {
    Assert.assertFalse(body.contains("table.next"));
  }

  private void assertPrevious(String body, int page) {
    Assert.assertTrue(body.contains("<a id=\"table-previous\" href=\"/controls/table/pagedTable.htm?page=" + page + "\">previous</a>"));
  }

  private void assertNext(String body, int page) {
    Assert.assertTrue(body.contains("<a id=\"table-next\" href=\"/controls/table/pagedTable.htm?page=" + page + "\">next</a>"));
  }

  private void assertPreviousWithRows(String body, int page, int rows) {
    Assert.assertTrue(body.contains("<a id=\"table-previous\" href=\"/controls/table/pagedTable.htm?page="
      + page
      + "&rows="
      + rows
      + "\">previous</a>"));
  }

  private void assertNextWithRows(String body, int page, int rows) {
    Assert.assertTrue(body.contains("<a id=\"table-next\" href=\"/controls/table/pagedTable.htm?page=" + page + "&rows=" + rows + "\">next</a>"));
  }

}
