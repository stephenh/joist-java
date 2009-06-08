package joist.domain.codegen;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import joist.domain.migrations.DatabaseBootstrapper;
import joist.domain.migrations.Migrater;
import joist.domain.migrations.MigraterConfig;
import joist.domain.migrations.PermissionFixer;
import joist.domain.util.ConnectionSettings;
import joist.domain.util.Pgc3p0Factory;
import joist.util.Inflector;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public abstract class AbstractJoistCli {

    public ConnectionSettings dbAppSettings;
    public ConnectionSettings dbSaSettings;
    public CodegenConfig codegenConfig = new CodegenConfig();
    public MigraterConfig migraterConfig = new MigraterConfig();
    private final Map<String, DataSource> dss = new HashMap<String, DataSource>();

    public AbstractJoistCli(String projectName) {
        this.dbAppSettings = ConnectionSettings.forApp(Inflector.underscore(projectName));
        this.dbSaSettings = ConnectionSettings.forSa(Inflector.underscore(projectName));
        this.migraterConfig.setProjectNameForDefaults(projectName);
        this.codegenConfig.setProjectNameForDefaults(projectName);
        this.codegenConfig.saUsername = this.dbSaSettings.user;
        if (".".equals(this.dbSaSettings.password)) {
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
            this.dbAppSettings).dropAndCreate();
    }

    public void migrateDatabase() {
        new Migrater(this.getDataSourceForAppTableAsSaUser(), this.migraterConfig).migrate();
    }

    public void fixPermissions() {
        PermissionFixer pf = new PermissionFixer(this.getDataSourceForAppTableAsSaUser());
        pf.setOwnerOfAllTablesTo(this.dbSaSettings.user);
        pf.setOwnerOfAllSequencesTo(this.dbSaSettings.user);
        pf.grantAllOnAllTablesTo(this.dbAppSettings.user);
        pf.grantAllOnAllSequencesTo(this.dbAppSettings.user);
    }

    public void codegen() {
        new Codegen(this.getDataSourceForAppTableAsSaUser(), this.codegenConfig).generate();
    }

    private DataSource getDataSourceForAppTableAsSaUser() {
        return this.getCachedDatasource(this.dbSaSettings.host, this.dbSaSettings.databaseName, this.dbSaSettings.user, this.dbSaSettings.password);
    }

    private DataSource getDataSourceForSystemTableAsSaUser() {
        return this.getCachedDatasource(this.dbSaSettings.host, "postgres", this.dbSaSettings.user, this.dbSaSettings.password);
    }

    private DataSource getCachedDatasource(String dbHost, String dbName, String username, String password) {
        Pgc3p0Factory.setDefaultc3p0Flags();
        String key = dbHost + "." + dbName + "." + username + "." + password;
        if (!this.dss.containsKey(key)) {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setJdbcUrl("jdbc:postgresql://" + dbHost + "/" + dbName);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(3);
            ds.setInitialPoolSize(1);
            this.dss.put(key, ds);
        }
        return this.dss.get(key);
    }

}
