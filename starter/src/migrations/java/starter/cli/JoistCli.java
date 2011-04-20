package starter.cli;

import joist.AbstractJoistCli;
import joist.domain.orm.Db;

public class JoistCli extends AbstractJoistCli {

  public JoistCli() {
    // change to Db.PG for postgres
    super("starter", Db.MYSQL);
  }

}
