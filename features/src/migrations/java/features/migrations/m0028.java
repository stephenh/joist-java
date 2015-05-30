package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import joist.migrations.AbstractMigration;

public class m0028 extends AbstractMigration {

  public m0028() {
    super("Parent with custom one-to-many names.");
  }

  public void apply() {
    createEntityTable("parent_i");
    createEntityTable("child_i_a", foreignKey("parent_id", "parent_i").ownerIsThem());
    createEntityTable("child_i_b", foreignKey("parent_id", "parent_i").ownerIsThem().unique());
  }
}
