package click.controls.table;

import org.exigencecorp.util.StringBuilderr;

public abstract class AbstractColumn implements Column {

    protected Table<?> table;

    public void renderHeader(StringBuilderr sb) {
    }

    public void renderRow(StringBuilderr sb) {
    }

    public void setTable(Table<?> table) {
        this.table = table;
    }

}
