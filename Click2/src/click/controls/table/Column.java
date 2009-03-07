package click.controls.table;

import click.Control;
import click.util.HtmlWriter;

public interface Column extends Control {

    // Should be on Control?
    String getFullId();

    void renderHeader(HtmlWriter sb);

    void setTable(Table<?> table);

    void setCurrentRowIndex(Integer index);

}
