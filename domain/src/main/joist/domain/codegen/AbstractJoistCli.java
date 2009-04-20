package joist.domain.codegen;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import joist.domain.migrations.DatabaseBootstrapper;
import joist.domain.migrations.Migrater;
import joist.domain.migrations.MigraterConfig;
import joist.domain.migrations.PermissionFixer;
import joist.domain.util.AbstractPgWithc3p0DataSourceFactory;
import joist.util.Inflector;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public abstract class AbstractJoistCli {

    public String dbHost;
    public String dbName;
    public String dbAppUsername;
    public String dbAppPassword;
    public String dbSaUsername;
    public String dbSaPassword;
    public CodegenConfig codegenConfig = new CodegenConfig();
    public MigraterConfig migraterConfig = new MigraterConfig();
    private final Map<String, DataSource> dss = new HashMap<String, DataSource>();

    public AbstractJoistCli(String projectName) {
        this.dbHost = System.getProperty("db.host", "localhost");
        this.dbName = System.getProperty("db.name", Inflector.underscore(projectName));
        this.dbAppUsername = System.getProperty("db.app.username", Inflector.underscore(projectName) + "_role");
        this.dbAppPassword = System.getProperty("db.app.password", Inflector.underscore(projectName) + "_role");
        this.dbSaUsername = System.getProperty("db.sa.username", "postgres");
        this.dbSaPassword = System.getProperty("db.sa.password", ".");
        this.migraterConfig.setProjectNameForDefaults(projectName);
        this.codegenConfig.setProjectNameForDefaults(projectName);
        if (".".equals(this.dbSaPassword)) {
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
            this.dbName,
            this.dbAppUsername,
            this.dbAppPassword).dropAndCreate();
    }

    public void migrateDatabase() {
        new Migrater(this.getDataSourceForAppTableAsSaUser(), this.migraterConfig).migrate();
    }

    public void fixPermissions() {
        PermissionFixer pf = new PermissionFixer(this.getDataSourceForAppTableAsSaUser());
        pf.setOwnerOfAllTablesTo(this.dbSaUsername);
        pf.setOwnerOfAllSequencesTo(this.dbAppUsername); // the app user needs to own them to reset them for tests--could fix by flushing as sa user
        pf.grantAllOnAllTablesTo(this.dbAppUsername);
        pf.grantAllOnAllSequencesTo(this.dbAppUsername);
    }

    public void codegen() {
        new Codegen(this.getDataSourceForAppTableAsSaUser(), this.codegenConfig).generate();
    }

    private DataSource getDataSourceForAppTableAsSaUser() {
        return this.getCachedDatasource(this.dbHost, this.dbName, this.dbSaUsername, this.dbSaPassword);
    }

    private DataSource getDataSourceForSystemTableAsSaUser() {
        return this.getCachedDatasource(this.dbHost, "postgres", this.dbSaUsername, this.dbSaPassword);
    }

    private DataSource getCachedDatasource(String dbHost, String dbName, String username, String password) {
        AbstractPgWithc3p0DataSourceFactory.setDefaultc3p0Flags();
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
