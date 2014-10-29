package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0026 extends AbstractMigration {

  public m0026() {
    super("Table with one-to-one foreign key that uses ownerIsThem.");
  }

  public void apply() {
    createEntityTable("parent_g", varchar("name"));
    createEntityTable("child_g",//
      foreignKey("parent_one_id", "parent_g").ownerIsThem().unique(),
      foreignKey("parent_two_id", "parent_g").ownerIsThem().nullable().unique(),
      varchar("name"));
  }
}
