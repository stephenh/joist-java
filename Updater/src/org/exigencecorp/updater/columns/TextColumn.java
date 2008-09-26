package org.exigencecorp.updater.columns;


public class TextColumn extends AbstractColumn {

    public TextColumn(String name) {
        this(name, Nullable.No);
    }

    public TextColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return this.getName() + " text,";
    }

}
