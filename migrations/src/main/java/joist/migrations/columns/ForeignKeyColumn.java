package joist.migrations.columns;

import java.util.ArrayList;
import java.util.List;

import joist.migrations.MigrationKeywords;
import joist.util.Interpolate;
import joist.util.Wrap;

public class ForeignKeyColumn extends AbstractColumn<ForeignKeyColumn> {

  public static ConstraintNamer constraintNamer = new HackyConstraintNamer();
  private String otherTable;
  private String otherTableColumn;
  private Owner owner;

  public enum Owner {
    IsMe, IsThem, IsNeither
  };

  public ForeignKeyColumn(String otherTable) {
    super(otherTable + "_id", PrimaryKeyColumn.keyColumnType);
    this.otherTable = otherTable;
    this.otherTableColumn = "id";
    this.owner = Owner.IsNeither;
  }

  public ForeignKeyColumn(String columnName, String otherTable, String otherTableColumn) {
    super(columnName, PrimaryKeyColumn.keyColumnType);
    if (!columnName.endsWith("id")) {
      throw new RuntimeException("names of fk columns should end with id");
    }
    this.otherTable = otherTable;
    this.otherTableColumn = otherTableColumn;
    this.owner = Owner.IsNeither;
  }

  public ForeignKeyColumn ownerIs(Owner owner) {
    this.owner = owner;
    return this;
  }

  public ForeignKeyColumn ownerIsMe() {
    return this.ownerIs(Owner.IsMe);
  }

  public ForeignKeyColumn ownerIsThem() {
    return this.ownerIs(Owner.IsThem);
  }

  public ForeignKeyColumn ownerIsNeither() {
    return this.ownerIs(Owner.IsNeither);
  }

  @Override
  public List<String> postInjectCommands() {
    List<String> sqls = super.postInjectCommands();
    sqls.addAll(this.constraintCommands());
    return sqls;
  }

  public List<String> constraintCommands() {
    List<String> sqls = new ArrayList<String>();
    String constraintName = constraintNamer.name(this.owner);
    String optionalCascade = this.owner == ForeignKeyColumn.Owner.IsThem ? " ON DELETE CASCADE" : "";
    String optionalDeferrable = (MigrationKeywords.isPg() ? " DEFERRABLE INITIALLY DEFERRED" : "");
    sqls.add(Interpolate.string(
      "ALTER TABLE {} ADD CONSTRAINT {} FOREIGN KEY ({}) REFERENCES {} ({}){}{};",
      Wrap.quotes(this.getTableName()),
      constraintName,
      Wrap.quotes(this.getName()),
      Wrap.quotes(this.otherTable),
      Wrap.quotes(this.otherTableColumn),
      optionalCascade,
      optionalDeferrable));
    // Foreign keys in MySQL are automatically indexed
    if (MigrationKeywords.isPg()) {
      String indexName = this.getTableName() + "_" + this.getName() + "_idx";
      // ideally we could CREATE INDEX IF NOT EXISTS to avoid the perf cost
      // of a DROP+CREATE, but this is easiest for now
      sqls.add("DROP INDEX IF EXISTS " + indexName);
      sqls.add(Interpolate.string(//
        "CREATE INDEX {} ON {} USING btree ({});",
        indexName,
        Wrap.quotes(this.getTableName()),
        Wrap.quotes(this.getName())));
    }
    return sqls;
  }
}
