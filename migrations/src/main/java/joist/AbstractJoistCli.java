package joist;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import joist.codegen.Codegen;
import joist.codegen.CodegenConfig;
import joist.domain.orm.Db;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.pools.MySqlC3p0Factory;
import joist.domain.util.pools.Pgc3p0Factory;
import joist.migrations.DatabaseBootstrapper;
import joist.migrations.Migrater;
import joist.migrations.MigraterConfig;
import joist.migrations.PermissionFixer;
import joist.util.Inflector;

public abstract class AbstractJoistCli {

  public ConnectionSettings dbAppUserSettings;
  public ConnectionSettings dbAppSaSettings;
  public ConnectionSettings dbSystemSettings;
  public CodegenConfig codegenConfig = new CodegenConfig();
  public MigraterConfig migraterConfig = new MigraterConfig();
  private final Map<ConnectionSettings, DataSource> dss = new HashMap<ConnectionSettings, DataSource>();

  public AbstractJoistCli(String projectName, Db db) {
    this.dbAppUserSettings = ConnectionSettings.forApp(db, Inflector.underscore(projectName));
    this.dbAppSaSettings = ConnectionSettings.forSa(db, Inflector.underscore(projectName));

    this.dbSystemSettings = ConnectionSettings.forSa(db, Inflector.underscore(projectName));
    this.dbSystemSettings.databaseName = db.isPg() ? "postgres" : "mysql";
    this.dbSystemSettings.password = this.dbAppSaSettings.password;

    this.migraterConfig.setProjectNameForDefaults(projectName);
    this.codegenConfig.setProjectNameForDefaults(projectName);
    if (".".equals(this.dbAppSaSettings.password)) {
      throw new RuntimeException("You need to set db.sa.password either on the command line or in build.properties.");
    }
  }

  public void cycle() {
    this.createDatabase();
    this.migrateDatabase();
    this.fixPermissions();
    this.codegen();
  }

  public void createDatabase() {
    new DatabaseBootstrapper(//
      this.getDataSourceForSystemTableAsSaUser(),
      this.getDataSourceForAppTableAsSaUser(),
      this.dbAppUserSettings).dropAndCreate();
  }

  public void migrateDatabase() {
    new Migrater(this.dbAppUserSettings, this.getDataSourceForAppTableAsSaUser(), this.migraterConfig).migrate();
  }

  public void fixPermissions() {
    PermissionFixer pf = new PermissionFixer(this.dbAppUserSettings, this.getDataSourceForAppTableAsSaUser());
    pf.grantAllOnAllTablesTo(this.dbAppUserSettings.user);
    if (this.dbAppUserSettings.db.isPg()) {
      pf.setOwnerOfAllTablesTo(this.dbAppSaSettings.user); // mysql doesn't have ownership
      pf.setOwnerOfAllSequencesTo(this.dbAppSaSettings.user);
      pf.grantAllOnAllSequencesTo(this.dbAppUserSettings.user);
    }
  }

  public void codegen() {
    new Codegen(this.dbAppUserSettings, this.getDataSourceForAppTableAsSaUser(), this.codegenConfig).generate();
  }

  private DataSource getDataSourceForAppTableAsSaUser() {
    return this.getCachedDatasource(this.dbAppSaSettings);
  }

  private DataSource getDataSourceForSystemTableAsSaUser() {
    return this.getCachedDatasource(this.dbSystemSettings);
  }

  private DataSource getCachedDatasource(ConnectionSettings settings) {
    if (!this.dss.containsKey(settings)) {
      DataSource ds = (this.dbAppSaSettings.db.isPg() ? new Pgc3p0Factory(settings) : new MySqlC3p0Factory(settings)).create();
      this.dss.put(settings, ds);
    }
    return this.dss.get(settings);
  }

}
