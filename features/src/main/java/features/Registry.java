package features;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.orm.Repository;
import joist.util.Log;
import joist.util.SystemProperties;

public class Registry {

  @SuppressWarnings("unused")
  private final static Registry instance = new Registry();

  public static void start() {
    Log.debug("Starting...");
  }

  public static DataSource getDataSource() {
    return Repository.datasource;
  }

  private Registry() {
    // mysql
    SystemProperties.loadFromFileIfExists("./build.properties");
    Repository.configure(Db.MYSQL, "features");

    // pg
    // SystemProperties.loadFromFileIfExists("./build-pg.properties");
    // Repository.configure(Db.PG, "features");
  }

}
