package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0018 extends AbstractMigration {

  public m0018() {
    super("Self referencing ParentE.");
  }

  public void apply() {
    createEntityTable("parent_e", foreignKey("parent_e").nullable(), varchar("name"));
  }

}
