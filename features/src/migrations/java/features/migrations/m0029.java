package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.foreignKey;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0029 extends AbstractMigration {

  public m0029() {
    super("Add a child entity of an sub entity.");
  }

  public void apply() {
    createEntityTable("inheritance_a_sub_one_child", varchar("name"), foreignKey("sub_id", "inheritance_a_sub_one"));
  }
}
