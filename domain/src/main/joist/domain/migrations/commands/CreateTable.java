package joist.domain.migrations.commands;

import java.util.ArrayList;
import java.util.List;

import joist.domain.migrations.columns.Column;
import joist.util.StringBuilderr;

public class CreateTable {

    private String name;
    private Column[] columns;

    public CreateTable(String name, Column... columns) {
        this.name = name;
        this.columns = columns;
    }

    public List<String> toSql() {
        List<String> sqls = new ArrayList<String>();

        for (Column column : this.columns) {
            column.setTableName(this.name);
            sqls.addAll(column.preInjectCommands());
        }

        StringBuilderr sb = new StringBuilderr();
        sb.line("CREATE TABLE {} (", this.name);
        for (Column column : this.columns) {
            sb.line(1, column.toSql() + ",");
        }
        sb.stripLastCharacterOnPreviousLine(); // Remove the last ,
        sb.line(") type = InnoDB;");
        sb.stripTrailingNewLine();
        sqls.add(sb.toString());

        for (Column column : this.columns) {
            sqls.addAll(column.postInjectCommands());
        }

        return sqls;
    }

    public String getName() {
        return this.name;
    }

    public Column[] getColumns() {
        return this.columns;
    }

}
