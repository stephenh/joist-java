package features;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.orm.Repository;
import joist.util.SystemProperties;

public class Registry {

  /** Should match {@link features.cli.JoistCli#db} for testing. */
  private static final Db db = Db.PG;
  private final static Registry instance = new Registry();
  private final Repository repository;

  public static void start() {
  }

  public static DataSource getDataSource() {
    return instance.repository.getDataSource();
  }

  public static Repository getRepository() {
    return instance.repository;
  }

  private Registry() {
    if (Registry.db.isMySQL()) {
      SystemProperties.loadFromFileIfExists("./build.properties");
      this.repository = new Repository(Db.MYSQL, "features");
    } else {
      SystemProperties.loadFromFileIfExists("./build-pg.properties");
      this.repository = new Repository(Db.PG, "features");
    }
  }

}
