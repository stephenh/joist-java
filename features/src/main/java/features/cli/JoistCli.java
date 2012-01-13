package features.cli;

import joist.AbstractJoistCli;
import joist.domain.orm.Db;
import joist.sourcegen.GSettings;

public class JoistCli extends AbstractJoistCli {

  public JoistCli() {
    super("features", Db.MYSQL);
    this.codegenConfig.outputCodegenDirectory = "src/codegen";
    this.codegenConfig.setCollectionSkipped("ParentD", "parentDChildAs");
    this.codegenConfig.setCollectionSkipped("ParentD", "parentDToChildCs");
    this.codegenConfig.setPropertySkipped("Primitives", "skipped");
    this.codegenConfig.setPropertySkipped("Primitives", "parent");
    GSettings.setDefaultIndentation("  ");
  }

}
