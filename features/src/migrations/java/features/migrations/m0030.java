package features.migrations;

import static joist.migrations.MigrationKeywords.addCode;
import static joist.migrations.MigrationKeywords.removeCode;
import joist.migrations.AbstractMigration;

public class m0030 extends AbstractMigration {

  public m0030() {
    super("Remove code.");
  }

  public void apply() {
    addCode("code_a_size", "THREE", "Three");
    removeCode("code_a_size", "THREE");
  }
}
