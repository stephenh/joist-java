package org.exigencecorp.updater.columns;


public class ByteaColumn extends AbstractColumn {

    public ByteaColumn(String name) {
        this(name, Nullable.Yes);
    }

    public ByteaColumn(String name, Nullable isNull) {
        super(name, isNull);
    }

    public String toSql() {
        return this.getName() + " bytea,";
    }

}
