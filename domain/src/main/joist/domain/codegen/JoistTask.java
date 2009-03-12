package joist.domain.codegen;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import joist.domain.migrations.DatabaseBootstrapper;
import joist.domain.migrations.Migrater;
import joist.domain.migrations.MigraterConfig;
import joist.domain.migrations.PermissionFixer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JoistTask {

    public String host = "localhost";
    public String databaseName;
    public String databaseAppUsername;
    public String databaseAppPassword;
    public String databaseSaUsername;
    public String databaseSaPassword;
    public CodegenConfig codegenConfig = new CodegenConfig();
    public MigraterConfig migraterConfig = new MigraterConfig();
    private final Map<String, DataSource> dss = new HashMap<String, DataSource>();

    public JoistTask(String projectName) {
        this.databaseName = projectName;
        this.databaseAppUsername = projectName + "_role";
        this.databaseAppPassword = projectName + "_role";
        this.databaseSaUsername = "sa";
        this.databaseSaPassword = "";
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
            this.databaseName,
            this.databaseAppUsername,
            this.databaseAppPassword).dropAndCreate();
    }

    public void migrateDatabase() {
        new Migrater(this.getDataSourceForAppTableAsSaUser(), this.migraterConfig).migrate();
    }

    public void fixPermissions() {
        PermissionFixer pf = new PermissionFixer(this.getDataSourceForAppTableAsSaUser());
        pf.setOwnerOfAllTablesTo(this.databaseSaUsername);
        pf.setOwnerOfAllSequencesTo(this.databaseAppUsername);
        pf.grantAllOnAllTablesTo(this.databaseAppUsername);
        pf.grantAllOnAllSequencesTo(this.databaseAppUsername);
    }

    public void codegen() {
        new Codegen(this.getDataSourceForAppTableAsSaUser(), this.codegenConfig).generate();
    }

    private DataSource getDataSourceForAppTableAsSaUser() {
        return this.getCachedDatasource(this.host, this.databaseName, this.databaseSaUsername, this.databaseSaPassword);
    }

    private DataSource getDataSourceForSystemTableAsSaUser() {
        return this.getCachedDatasource(this.host, "postgres", this.databaseSaUsername, this.databaseSaPassword);
    }

    private DataSource getCachedDatasource(String host, String databaseName, String username, String password) {
        String key = host + "." + databaseName + "." + username + "." + password;
        if (!this.dss.containsKey(key)) {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setJdbcUrl("jdbc:postgresql://" + host + "/" + databaseName);
            ds.setUser(username);
            ds.setPassword(password);
            ds.setMaxPoolSize(3);
            ds.setInitialPoolSize(1);
            this.dss.put(key, ds);
        }
        return this.dss.get(key);
    }

}
