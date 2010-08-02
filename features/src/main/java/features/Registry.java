package features;

import javax.sql.DataSource;

import joist.domain.orm.Db;
import joist.domain.orm.Repository;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.pools.Pgc3p0Factory;
import joist.registry.ResourceRef;
import joist.registry.ResourceRefs;
import joist.util.Log;
import joist.util.SystemProperties;

public class Registry {

  private final static Registry instance = new Registry();
  private final ResourceRefs refs = new ResourceRefs();
  private final ResourceRef<DataSource> appDatasource;

  public static void start() {
    Registry.instance.start2();
  }

  public static void stop() {
    Registry.instance.stop2();
  }

  public static DataSource getDataSource() {
    return Registry.instance.appDatasource.get();
  }

  private Registry() {
    // mysql
    // SystemProperties.loadFromFileIfExists("./build.properties");
    // Repository.db = Db.MYSQL;
    // this.appDatasource = this.refs.newRef(DataSource.class).factory(new MySqlC3p0Factory(ConnectionSettings.forApp(Db.MYSQL, "features"))).make();

    // pg
    SystemProperties.loadFromFileIfExists("./build-pg.properties");
    Repository.db = Db.PG;
    this.appDatasource = this.refs.newRef(DataSource.class).factory(new Pgc3p0Factory(ConnectionSettings.forApp(Db.PG, "features"))).make();

    Repository.datasource = this.appDatasource.get();
  }

  private void start2() {
    Log.debug("Starting...");
  }

  private void stop2() {
    this.refs.stop();
  }

}
