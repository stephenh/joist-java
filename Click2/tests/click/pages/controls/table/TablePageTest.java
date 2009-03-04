package click.pages.controls.table;

import junit.framework.Assert;

import org.exigencecorp.util.Join;

import click.pages.AbstractClickPageTest;

public class TablePageTest extends AbstractClickPageTest {

    public void testInitialGet() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<table id=table>",
            "  <thead>",
            "    <tr>",
            "      <th id=\"table.string\">string</th>",
            "      <th id=\"table.lowerCase\">lowerCase</th>",
            "    </tr>",
            "  </thead>",
            "  <tbody>",
            "    <tr>",
            "      <td id=\"table.string.0\">One</td>",
            "      <td id=\"table.lowerCase.0\">one</td>",
            "    </tr>",
            "    <tr>",
            "      <td id=\"table.string.1\">Two</td>",
            "      <td id=\"table.lowerCase.1\">two</td>",
            "    </tr>",
            "  </tbody>",
            "</table>",
            "",
            ""), this.request("/controls/table/table.htm").getBody());
    }
}
