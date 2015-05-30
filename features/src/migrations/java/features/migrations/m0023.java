package features.migrations;

import static joist.migrations.MigrationKeywords.renameColumn;
import joist.migrations.AbstractMigration;

public class m0023 extends AbstractMigration {

  public m0023() {
    super("Rename column.");
  }

  public void apply() {
    renameColumn("parent", "name", "name_2");
    renameColumn("parent", "name_2", "name");
  }
}
