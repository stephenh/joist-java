package joist.domain.migrations.commands;

import joist.domain.migrations.columns.Column;
import joist.util.StringBuilderr;

public class CreateTable {

    private String name;
    private Column[] columns;

    public CreateTable(String name, Column... columns) {
        this.name = name;
        this.columns = columns;
    }

    public String toSql() {
        StringBuilderr sb = new StringBuilderr();

        for (Column column : this.columns) {
            column.setTableName(this.name);
            column.preInjectCommands(sb);
        }

        sb.line("CREATE TABLE \"{}\" (", this.name);
        for (Column column : this.columns) {
            sb.line(1, column.toSql() + ",");
        }
        sb.stripLastCharacterOnPreviousLine(); // Remove the last ,
        sb.line(");");

        for (Column column : this.columns) {
            column.postInjectCommands(sb);
        }

        sb.stripTrailingNewLine();

        return sb.toString();
    }

    public String getName() {
        return this.name;
    }

    public Column[] getColumns() {
        return this.columns;
    }

}
