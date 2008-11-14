package org.exigencecorp.domainobjects.updater.columns;

import org.exigencecorp.domainobjects.updater.Updater;
import org.exigencecorp.jdbc.Jdbc;
import org.exigencecorp.util.StringBuilderr;

public class ForeignKeyColumn extends AbstractColumn {

    private String otherTable;
    private String otherTableColumn;
    private Owner owner;

    public enum Owner {
        IsMe, IsThem, IsNeither
    };

    public ForeignKeyColumn(String otherTable, Owner owner) {
        this(otherTable + "_id", otherTable, "id", Nullable.No, owner);
    }

    public ForeignKeyColumn(String otherTable, Owner owner, Nullable isNull) {
        this(otherTable + "_id", otherTable, "id", isNull, owner);
    }

    public ForeignKeyColumn(String name, String otherTable, Owner owner) {
        this(name, otherTable, "id", Nullable.No, owner);
    }

    public ForeignKeyColumn(String name, String otherTable, String otherTableColumn, Nullable isNull, Owner owner) {
        super(name, isNull);
        if (!name.endsWith("id")) {
            throw new RuntimeException("names of fk columns should end with id");
        }
        this.otherTable = otherTable;
        this.otherTableColumn = otherTableColumn;
        this.owner = owner;
    }

    public String toSql() {
        return this.getName() + " int,";
    }

    @Override
    public void postInjectCommands(StringBuilderr sb) {
        super.postInjectCommands(sb);

        int constraintNumber = (Updater.getConnection() == null) ? 1 : Jdbc.queryForInt(
            Updater.getConnection(),
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
