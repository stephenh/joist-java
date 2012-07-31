package features.migrations;

import static joist.migrations.MigrationKeywords.createEntityTable;
import static joist.migrations.MigrationKeywords.datetime;
import static joist.migrations.MigrationKeywords.varchar;
import joist.migrations.AbstractMigration;

public class m0022 extends AbstractMigration {

  public m0022() {
    super("Nullable datetime column.");
  }

  public void apply() {
    createEntityTable("values_b", varchar("name"), datetime("start").nullable());
  }

}
