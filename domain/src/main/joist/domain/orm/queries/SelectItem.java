package joist.domain.orm.queries;

import joist.domain.DomainObject;
import joist.domain.orm.queries.columns.AliasColumn;

public class SelectItem<T extends DomainObject> {

    private final String text;

    public SelectItem(AliasColumn<T, ?, ?> column) {
        this.text = column.getQualifiedName();
    }

    public SelectItem(AliasColumn<T, ?, ?> column, String as) {
        this.text = column.getQualifiedName() + " as " + as;
    }

    public SelectItem(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }

}
