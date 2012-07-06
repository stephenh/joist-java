package joist.migrations.columns;

import joist.migrations.MigrationKeywords;

public class PrimaryKeyColumn extends AbstractColumn<PrimaryKeyColumn> {

  /** Allow users to opt-in to something like {@code int unsigned}. */
  public static String keyColumnType = "int";

  public enum UseSequence {
    Yes, No
  };

  private UseSequence useSequence = PrimaryKeyColumn.UseSequence.Yes;
  private String sequenceName = null;

  public PrimaryKeyColumn(String name) {
    super(name, keyColumnType);
  }

  public String toSql() {
    if (MigrationKeywords.db.isPg() && this.useSequence == PrimaryKeyColumn.UseSequence.Yes) {
      return super.getQuotedName() + " SERIAL PRIMARY KEY";
    } else {
      return super.toSql() + " PRIMARY KEY";
    }
  }

  public String getSequenceName() {
    if (this.sequenceName != null) {
      return this.sequenceName;
    }
    return this.getTableName() + "_" + this.getName() + "_seq";
  }

  public PrimaryKeyColumn noSequence() {
    this.useSequence = UseSequence.No;
    return this;
  }

  @Override
  public String getDataType() {
    if (MigrationKeywords.db.isMySQL() && this.useSequence == PrimaryKeyColumn.UseSequence.Yes) {
      // Add AUTO_INCREMENT here so it gets used for add/remove not null
      return keyColumnType + " AUTO_INCREMENT";
    } else {
      return super.getDataType();
    }
  }

}
