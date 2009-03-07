package click.controls.table;

import click.util.HtmlWriter;

public interface Column {

    void setTable(Table<?> table);

    void renderHeader(HtmlWriter sb);

    void renderRow(HtmlWriter sb);

    String getName();

}
