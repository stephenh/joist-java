package org.exigencecorp.domainobjects.migrations.columns;

public class TextColumn extends AbstractColumn<TextColumn> {

    public TextColumn(String name) {
        super(name);
    }

    public String toSql() {
        return this.getName() + " text,";
    }

}
