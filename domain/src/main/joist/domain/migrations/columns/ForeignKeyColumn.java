package joist.domain.migrations.columns;

import joist.domain.migrations.Migrater;
import joist.jdbc.Jdbc;
import joist.util.StringBuilderr;

public class ForeignKeyColumn extends AbstractColumn<ForeignKeyColumn> {

    private String otherTable;
    private String otherTableColumn;
    private Owner owner;

    private enum Owner {
        IsMe, IsThem, IsNeither
    };

    public ForeignKeyColumn(String otherTable) {
        super(otherTable + "_id");
        this.otherTable = otherTable;
        this.otherTableColumn = "id";
        this.owner = Owner.IsThem;
    }

    public ForeignKeyColumn(String columnName, String otherTable, String otherTableColumn) {
        super(columnName);
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

    public ForeignKeyColumn ownerIsNeither() {
        this.owner = Owner.IsNeither;
        return this;
    }

    public String toSql() {
        return this.getName() + " int,";
    }

    @Override
    public void postInjectCommands(StringBuilderr sb) {
        super.postInjectCommands(sb);

        int constraintNumber = (Migrater.getConnection() == null) ? 1 : Jdbc.queryForInt(
            Migrater.getConnection(),
            "select nextval('constraint_name_seq')");
        String constraintName = "constraint_" + constraintNumber + "_owner_" + this.owner.toString().toLowerCase() + "_fk";
        String indexName = this.getTableName() + "_" + this.getName() + "_idx";

        sb.append(
            "ALTER TABLE \"{}\" ADD CONSTRAINT \"{}\" FOREIGN KEY (\"{}\") REFERENCES \"{}\" (\"{}\")",
            this.getTableName(),
            constraintName,
            this.getName(),
            this.otherTable,
            this.otherTableColumn);
        if (this.owner == ForeignKeyColumn.Owner.IsThem) {
            sb.append(" ON DELETE CASCADE");
        }
        sb.line(" DEFERRABLE;");
        sb.line("CREATE INDEX \"{}\" ON \"{}\" USING btree ({});", indexName, this.getTableName(), this.getName());
    }

}
