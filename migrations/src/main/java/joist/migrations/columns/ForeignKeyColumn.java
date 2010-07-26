package joist.migrations.columns;

import java.util.List;

import joist.util.Interpolate;

public class ForeignKeyColumn extends AbstractColumn<ForeignKeyColumn> {

    // We need something short (64 char max constraint names), different, and
    // that will change each time we apply a new column. nanos might also work.
    private static long hackyNextId = System.currentTimeMillis();
    private String otherTable;
    private String otherTableColumn;
    private Owner owner;

    private enum Owner {
        IsMe, IsThem, IsNeither
    };

    public ForeignKeyColumn(String otherTable) {
        super(otherTable + "_id", "int");
        this.otherTable = otherTable;
        this.otherTableColumn = "id";
        this.owner = Owner.IsNeither;
    }

    public ForeignKeyColumn(String columnName, String otherTable, String otherTableColumn) {
        super(columnName, "int");
        if (!columnName.endsWith("id")) {
            throw new RuntimeException("names of fk columns should end with id");
        }
        this.otherTable = otherTable;
        this.otherTableColumn = otherTableColumn;
        this.owner = Owner.IsNeither;
    }

    public ForeignKeyColumn ownerIsMe() {
        this.owner = Owner.IsMe;
        return this;
    }

    public ForeignKeyColumn ownerIsThem() {
        this.owner = Owner.IsThem;
        return this;
    }

    public ForeignKeyColumn ownerIsNeither() {
        this.owner = Owner.IsNeither;
        return this;
    }

    @Override
    public List<String> postInjectCommands() {
        List<String> sqls = super.postInjectCommands();

        String constraintName = Interpolate.string("{}_{}_fk",//
            (ForeignKeyColumn.hackyNextId++),
            this.owner.toString().toLowerCase());
        sqls.add(Interpolate.string(
            "ALTER TABLE `{}` ADD CONSTRAINT {} FOREIGN KEY (`{}`) REFERENCES `{}` (`{}`);",
            this.getTableName(),
            constraintName,
            this.getName(),
            this.otherTable,
            this.otherTableColumn));
        if (this.owner == ForeignKeyColumn.Owner.IsThem) {
            // sb.append(" ON DELETE CASCADE");
        }
        // sb.line(" DEFERRABLE;");
        // sb.line("CREATE INDEX \"{}\" ON \"{}\" USING btree ({});", indexName, this.getTableName(), this.getName());

        return sqls;
    }
}
