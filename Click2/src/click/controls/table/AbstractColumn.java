package click.controls.table;

import org.exigencecorp.util.StringBuilderr;

public abstract class AbstractColumn implements Column {

    protected Table table;

    public void renderHeader(StringBuilderr sb) {
        // TODO Auto-generated method stub
    }

    public void renderRow(StringBuilderr sb) {
        // TODO Auto-generated method stub

    }

    public void setTable(Table table) {
        this.table = table;
    }

}
