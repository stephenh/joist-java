package joist.domain.codegen;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import joist.domain.migrations.DatabaseBootstrapper;
import joist.domain.migrations.Migrater;
import joist.domain.migrations.MigraterConfig;
import joist.domain.migrations.PermissionFixer;

import org.h2.jdbcx.JdbcConnectionPool;

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
        this.dbName = System.getProperty("db.name", projectName);
        this.dbAppUsername = System.getProperty("db.app.username", projectName + "_role");
        this.dbAppPassword = System.getProperty("db.app.password", projectName + "_role");
        this.dbSaUsername = System.getProperty("db.sa.username", "sa");
        this.dbSaPassword = System.getProperty("db.sa.password", "");
        this.migraterConfig.setProjectNameForDefaults(projectName);
        this.codegenConfig.setProjectNameForDefaults(projectName);
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
        pf.setOwnerOfAllSequencesTo(this.dbAppUsername);
        pf.grantAllOnAllTablesTo(this.dbAppUsername);
        pf.grantAllOnAllSequencesTo(this.dbAppUsername);
    }

    public void codegen() {
        new Codegen(this.getDataSourceForAppTableAsSaUser(), this.codegenConfig).generate();
    }

    private DataSource getDataSourceForAppTableAsSaUser() {
        return JdbcConnectionPool.create("jdbc:h2:mem:foo;DB_CLOSE_DELAY=-1", "", "");
        // return this.getCachedDatasource(this.host, this.databaseName, this.databaseSaUsername, this.databaseSaPassword);
    }

    private DataSource getDataSourceForSystemTableAsSaUser() {
        return JdbcConnectionPool.create("jdbc:h2:mem:foo;DB_CLOSE_DELAY=-1", "", "");
        // return this.getCachedDatasource(this.host, "postgres", this.databaseSaUsername, this.databaseSaPassword);
    }

    private DataSource getCachedDatasource(String dbHost, String dbName, String username, String password) {
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
