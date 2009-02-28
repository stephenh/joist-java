package click.pages.controls.table;

import junit.framework.Assert;

import org.exigencecorp.util.Join;

import click.pages.AbstractClickPageTest;

public class TablePageTest extends AbstractClickPageTest {

    public void testInitialGet() throws Exception {
        Assert.assertEquals(Join.lines(
            "",
            "<table id=foo>",
            "  <thead>",
            "    <tr>",
            "<th>string</th>",
            "<th>lowerCase</th>",
            "    </tr>",
            "  </thead>",
            "  <tbody>",
            "    <tr>",
            "<td>One</td>",
            "<td>one</td>",
            "    </tr>",
            "    <tr>",
            "<td>Two</td>",
            "<td>two</td>",
            "    </tr>",
            "  </tbody>",
            "</table>",
            "",
            ""), this.request("/controls/table/table.htm").getBody());
    }
}
