package click.controls.table;

import org.exigencecorp.util.StringBuilderr;

public interface Column {

    void setTable(Table table);

    void renderHeader(StringBuilderr sb);

    void renderRow(StringBuilderr sb);

}
