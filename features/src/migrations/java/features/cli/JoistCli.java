package features.cli;

import joist.AbstractJoistCli;
import joist.codegen.Config;
import joist.domain.orm.Db;
import joist.sourcegen.GSettings;
import joist.util.SystemProperties;

public class JoistCli extends AbstractJoistCli {

  /** Should match {@link features.Registry#db} for testing. */
  public static final Db db = Db.PG;

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
  }

  public static Config config() {
    Config config = new Config("features", db);
    config.outputCodegenDirectory = "src/codegen/java";
    // config.setCollectionSkipped("ParentD", "parentDChildAs");
    // config.setCollectionSkipped("ParentD", "parentDToChildCs");
    // config.setPropertySkipped("Primitives", "skipped");
    // config.setPropertySkipped("Primitives", "parent");
    // config.setOneToManyPropertyName("child_i_a", "parent_id", "childA");
    // config.setOneToManyPropertyName("child_i_b", "parent_id", "childB");
    return config;
  }

}
