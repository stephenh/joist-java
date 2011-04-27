package features.cli;

import joist.AbstractJoistCli;
import joist.domain.orm.Db;

public class JoistCli extends AbstractJoistCli {

  public JoistCli() {
    super("features", Db.MYSQL);
    this.codegenConfig.outputCodegenDirectory = "src/codegen";
    this.codegenConfig.setCollectionSkipped("ParentD", "parentDChildAs");
    this.codegenConfig.setCollectionSkipped("ParentD", "parentDToChildCs");
  }

}
