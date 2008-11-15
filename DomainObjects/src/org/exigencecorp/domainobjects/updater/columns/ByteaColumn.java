package org.exigencecorp.domainobjects.updater.columns;

public class ByteaColumn extends AbstractColumn<ByteaColumn> {

    public ByteaColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " bytea,";
    }

}
