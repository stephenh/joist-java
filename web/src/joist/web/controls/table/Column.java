package joist.web.controls.table;

import joist.web.Control;
import joist.web.util.HtmlWriter;

public interface Column extends Control {

    // Should be on Control?
    String getFullId();

    void renderHeader(HtmlWriter sb);

    void setTable(Table<?> table);

    void setCurrentRowIndex(Integer index);

}
