package org.exigencecorp.domainobjects.queries;

import org.exigencecorp.domainobjects.queries.columns.AliasColumn;

public class SetItem {

    private final AliasColumn<?, ?, ?> column;
    private final Object value;

    public SetItem(AliasColumn<?, ?, ?> column, Object value) {
        this.column = column;
        this.value = value;
    }

    public AliasColumn<?, ?, ?> getColumn() {
        return this.column;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        return this.column.getQualifiedName();
    }

}
