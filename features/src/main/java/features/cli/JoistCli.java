package features.cli;

import joist.AbstractJoistCli;
import joist.domain.orm.Db;
import joist.sourcegen.GSettings;

public class JoistCli extends AbstractJoistCli {

  public JoistCli() {
    super("features", Db.MYSQL);
    this.config.outputCodegenDirectory = "src/codegen";
    this.config.setCollectionSkipped("ParentD", "parentDChildAs");
    this.config.setCollectionSkipped("ParentD", "parentDToChildCs");
    this.config.setPropertySkipped("Primitives", "skipped");
    this.config.setPropertySkipped("Primitives", "parent");
    GSettings.setDefaultIndentation("  ");
    if (this.config.dbAppSaSettings.db.isMySQL()) {
      this.config.includeHistoryTriggers();
    }
  }

}
