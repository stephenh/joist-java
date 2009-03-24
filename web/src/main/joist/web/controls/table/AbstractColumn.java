package joist.web.controls.table;

import joist.util.Inflector;
import joist.web.AbstractControl;
import joist.web.util.HtmlWriter;

public abstract class AbstractColumn<T extends AbstractColumn<T>> extends AbstractControl implements Column {

    private String label;
    private Integer currentRowIndex;

    protected AbstractColumn(String id) {
        this.id(id);
    }

    // http://www.angelikalanger.com/GenericsFAQ/FAQSections/ProgrammingIdioms.html#What is the getThis trick?
    protected abstract T getThis();

    public T id(String id) {
        this.setId(id);
        this.setLabel(Inflector.humanize(id));
        return this.getThis();
    }

    public void renderHeader(HtmlWriter sb) {
        if (this.getLabel() != null) {
            sb.append(this.getLabel());
        }
    }

    public String getFullId() {
        String prefix = this.getParent() == null ? "" : this.getParent().getId() + ".";
        String suffix = this.currentRowIndex == null ? "" : "." + this.currentRowIndex;
        return prefix + this.getId() + suffix;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getCurrentRowIndex() {
        return this.currentRowIndex;
    }

    public void setCurrentRowIndex(Integer currentRowIndex) {
        this.currentRowIndex = currentRowIndex;
    }

}
