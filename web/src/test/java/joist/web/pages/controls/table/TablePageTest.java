package joist.web.pages.controls.table;

import joist.util.Join;
import joist.web.pages.AbstractClickPageTest;
import junit.framework.Assert;

public class TablePageTest extends AbstractClickPageTest {

  public void testInitialGet() throws Exception {
    Assert.assertEquals(Join.lines(
      "",
      "<h3>Table</h3>",
      "<table id=\"table\">",
      "  <thead>",
      "    <tr>",
      "      <th id=\"table-toStringBinding\">To String Binding</th>",
      "      <th id=\"table-toLowerCase\">To Lower Case</th>",
      "    </tr>",
      "  </thead>",
      "  <tbody>",
      "    <tr>",
      "      <td id=\"table-toStringBinding-0\">One</td>",
      "      <td id=\"table-toLowerCase-0\">one</td>",
      "    </tr>",
      "    <tr>",
      "      <td id=\"table-toStringBinding-1\">Two</td>",
      "      <td id=\"table-toLowerCase-1\">two</td>",
      "    </tr>",
      "  </tbody>",
      "</table>",
      "",
      ""), this.request("/controls/table/table.htm").getBody());
  }
}
