package click.controls.table;


public abstract class AbstractColumn implements Column {

    protected Table<?> table;

    public Table<?> getTable() {
        return this.table;
    }

    public void setTable(Table<?> table) {
        this.table = table;
    }

}
