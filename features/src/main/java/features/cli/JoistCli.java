package features.cli;

import joist.AbstractJoistCli;
import joist.domain.orm.Db;
import joist.sourcegen.GSettings;
import joist.util.SystemProperties;

public class JoistCli extends AbstractJoistCli {

  public static final Db db = Db.MYSQL;

  static {
    if (db.isMySQL()) {
      SystemProperties.loadFromFileIfExists("./build.properties");
    } else {
      SystemProperties.loadFromFileIfExists("./build-pg.properties");
    }
  }

  public JoistCli() {
    super("features", db);
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
