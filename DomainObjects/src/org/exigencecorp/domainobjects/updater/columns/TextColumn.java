package org.exigencecorp.domainobjects.updater.columns;

public class TextColumn extends AbstractColumn<TextColumn> {

    public TextColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " text,";
    }

}
