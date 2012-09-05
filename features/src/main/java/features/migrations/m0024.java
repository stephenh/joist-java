package features.migrations;

import static joist.migrations.MigrationKeywords.changeOwner;
import joist.migrations.AbstractMigration;
import joist.migrations.columns.ForeignKeyColumn.Owner;

public class m0024 extends AbstractMigration {

  public m0024() {
    super("Change owner.");
  }

  public void apply() {
    changeOwner("child", "parent_id", Owner.IsMe);
    changeOwner("child", "parent_id", Owner.IsThem);
  }
}
