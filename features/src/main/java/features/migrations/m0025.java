package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0025 extends AbstractMigration {

  public m0025() {
    super("Table with foreign key that uses ownerIsMe.");
  }

  public void apply() {
    createEntityTable("child_f", varchar("name"));
    createEntityTable("parent_f", //
      foreignKey("child_one_id", "child_f", "id").ownerIsMe(),
      foreignKey("child_two_id", "child_f", "id").ownerIsMe().nullable(),
      varchar("name"));
  }
}
