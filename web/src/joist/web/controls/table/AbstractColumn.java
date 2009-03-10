package joist.web.controls.table;

import joist.web.util.HtmlWriter;

import org.exigencecorp.util.Inflector;


public abstract class AbstractColumn<T extends AbstractColumn<T>> implements Column {

    protected Table<?> table;
    private String id;
    private String label;
    private Integer currentRowIndex;

    protected AbstractColumn(String id) {
        this.id = id;
        this.label = Inflector.humanize(id);
    }

    // http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#What is the getThis trick?
    protected abstract T getThis();

    public T id(String id) {
        this.id = id;
        this.label = Inflector.humanize(id);
        return this.getThis();
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

    public String getFullId() {
        String prefix = this.table == null ? "" : this.table.getId() + ".";
        String suffix = this.currentRowIndex == null ? "" : "." + this.currentRowIndex;
        return prefix + this.getId() + suffix;
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

    public Integer getCurrentRowIndex() {
        return this.currentRowIndex;
    }

    public void setCurrentRowIndex(Integer currentRowIndex) {
        this.currentRowIndex = currentRowIndex;
    }

}
