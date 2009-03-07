package click.controls.table;

import click.Control;
import click.util.HtmlWriter;

public interface Column extends Control {

    void setTable(Table<?> table);

    void renderHeader(HtmlWriter sb);

}
