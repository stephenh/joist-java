package features.cli;

import joist.AbstractJoistCli;
import joist.codegen.Config;
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
    super(config());
    GSettings.setDefaultIndentation("  ");
    if (this.config.dbAppSaSettings.db.isMySQL()) {
      this.config.includeHistoryTriggers();
    }
  }

  public static Config config() {
    Config config = new Config("features", db);
    config.outputCodegenDirectory = "src/codegen";
    config.setCollectionSkipped("ParentD", "parentDChildAs");
    config.setCollectionSkipped("ParentD", "parentDToChildCs");
    config.setPropertySkipped("Primitives", "skipped");
    config.setPropertySkipped("Primitives", "parent");
    return config;
  }

}
