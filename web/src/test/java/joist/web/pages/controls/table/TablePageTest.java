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
            "      <th id=\"table-string\">String</th>",
            "      <th id=\"table-lowerCase\">Lower Case</th>",
            "    </tr>",
            "  </thead>",
            "  <tbody>",
            "    <tr>",
            "      <td id=\"table-string-0\">One</td>",
            "      <td id=\"table-lowerCase-0\">one</td>",
            "    </tr>",
            "    <tr>",
            "      <td id=\"table-string-1\">Two</td>",
            "      <td id=\"table-lowerCase-1\">two</td>",
            "    </tr>",
            "  </tbody>",
            "</table>",
            "",
            ""), this.request("/controls/table/table.htm").getBody());
    }
}
