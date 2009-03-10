package joist.domain.queries;

import joist.domain.queries.columns.AliasColumn;

public class SelectItem {

    private final String text;

    public SelectItem(AliasColumn<?, ?, ?> column) {
        this.text = column.getQualifiedName();
    }

    public SelectItem(AliasColumn<?, ?, ?> column, String as) {
        this.text = column.getQualifiedName() + " as " + as;
    }

    public SelectItem(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }

}
