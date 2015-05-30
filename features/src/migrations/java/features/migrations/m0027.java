package features.migrations;

import static joist.migrations.MigrationKeywords.bigint;
import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0027 extends AbstractMigration {

  public m0027() {
    super("Parent with threshold and children with quantity. Interesting query is for children with sum(quantity) over threshold");
  }

  public void apply() {
    createEntityTable("parent_h", varchar("name"), bigint("threshold"));
    createEntityTable("child_h",//
      foreignKey("parent_id", "parent_h").ownerIsThem(),
      varchar("name"),
      bigint("quantity"));
  }
}
