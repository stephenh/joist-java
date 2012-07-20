package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.integer;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0019 extends AbstractMigration {

  public m0019() {
    super("dropNotNull part 1.");
  }

  public void apply() {
    createEntityTable("values_a", varchar("name"), integer("i"));
  }

}
