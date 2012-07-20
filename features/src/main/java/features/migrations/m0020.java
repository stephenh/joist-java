package features.migrations;

import static joist.migrations.MigrationKeywords.dropNotNull;
import joist.migrations.AbstractMigration;

public class m0020 extends AbstractMigration {

  public m0020() {
    super("dropNotNull part 2.");
  }

  public void apply() {
    dropNotNull("values_a", "i");
  }

}
