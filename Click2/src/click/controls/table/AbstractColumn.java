package click.controls.table;

import org.exigencecorp.util.Inflector;

import click.util.HtmlWriter;

public abstract class AbstractColumn implements Column {

    private String id;
    private String label;
    protected Table<?> table;

    protected AbstractColumn(String id) {
        this.id = id;
        this.label = Inflector.humanize(id);
    }

    public void onProcess() {
    }

    public void renderHeader(HtmlWriter sb) {
        if (this.getLabel() != null) {
            sb.append(this.getLabel());
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Table<?> getTable() {
        return this.table;
    }

    public void setTable(Table<?> table) {
        this.table = table;
    }

}
