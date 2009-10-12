package joist.domain.migrations.columns;

import joist.util.Interpolate;
import joist.util.StringBuilderr;

public class ForeignKeyColumn extends AbstractColumn<ForeignKeyColumn> {

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
        this.owner = Owner.IsThem;
    }

    public ForeignKeyColumn(String columnName, String otherTable, String otherTableColumn) {
        super(columnName, "int");
        if (!columnName.endsWith("id")) {
            throw new RuntimeException("names of fk columns should end with id");
        }
        this.otherTable = otherTable;
        this.otherTableColumn = otherTableColumn;
        this.owner = Owner.IsThem;
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
    public void postInjectCommands(StringBuilderr sb) {
        super.postInjectCommands(sb);
        String constraintName = Interpolate.string("constraint_{}_{}_owner_{}_fk",//
            this.getName(),
            System.currentTimeMillis(),
            this.owner.toString().toLowerCase());
        sb.append("ALTER TABLE \"{}\" ADD CONSTRAINT {} FOREIGN KEY (\"{}\") REFERENCES \"{}\" (\"{}\")",//
            this.getTableName(),
            constraintName,
            this.getName(),
            this.otherTable,
            this.otherTableColumn);
        if (this.owner == ForeignKeyColumn.Owner.IsThem) {
            sb.append(" ON DELETE CASCADE");
        }
        sb.line(" DEFERRABLE;");
        String indexName = this.getTableName() + "_" + this.getName() + "_idx";
        sb.line("CREATE INDEX \"{}\" ON \"{}\" USING btree ({});", indexName, this.getTableName(), this.getName());
    }

}
