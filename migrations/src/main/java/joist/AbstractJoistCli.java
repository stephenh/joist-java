package joist;

import joist.codegen.Codegen;
import joist.codegen.Config;
import joist.domain.orm.Db;
import joist.migrations.DatabaseBootstrapper;
import joist.migrations.Migrater;
import joist.migrations.PermissionFixer;
import joist.util.Inflector;

public abstract class AbstractJoistCli {

  public Config config;

  public AbstractJoistCli(String projectName, Db db) {
    this(projectName, Inflector.underscore(projectName), db);
  }

  public AbstractJoistCli(String projectName, String defaultDatabaseName, Db db) {
    this.config = new Config(projectName, defaultDatabaseName, db);
    if (".".equals(this.config.dbAppSaSettings.password)) {
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
    new DatabaseBootstrapper(this.config).dropAndCreate();
  }

  public void migrateDatabase() {
    new Migrater(this.config).migrate();
  }

  public void fixPermissions() {
    PermissionFixer pf = new PermissionFixer(this.config);
    pf.grantAllOnAllTablesTo(this.config.dbAppUserSettings.user);
    if (this.config.db.isPg()) {
      // mysql doesn't have the concept of ownership (...afaik)
      pf.setOwnerOfAllTablesTo(this.config.dbAppSaSettings.user);
      pf.setOwnerOfAllSequencesTo(this.config.dbAppSaSettings.user);
      pf.grantAllOnAllSequencesTo(this.config.dbAppUserSettings.user);
    }
  }

  public void codegen() {
    new Codegen(this.config).generate();
  }

}
