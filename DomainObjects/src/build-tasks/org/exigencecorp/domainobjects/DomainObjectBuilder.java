package org.exigencecorp.domainobjects;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;
import org.exigencecorp.domainobjects.migrations.DatabaseBootstrapper;
import org.exigencecorp.domainobjects.migrations.Migrater;
import org.exigencecorp.domainobjects.migrations.MigraterConfig;
import org.exigencecorp.domainobjects.migrations.PermissionFixer;
import org.exigencecorp.util.Reflection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DomainObjectBuilder {

    public String databaseName;
    public String databaseAppUsername;
    public String databaseAppPassword;
    public String databaseSaUsername;
    public String databaseSaPassword;
    public CodegenConfig codegenConfig = new CodegenConfig();
    public MigraterConfig migraterConfig = new MigraterConfig();
    private DataSource appTableAsSaUser;
    private DataSource systemTableAsSaUser;

    public DomainObjectBuilder(String projectName) {
        this.databaseName = projectName;
        this.databaseAppUsername = projectName + "_role";
        this.databaseAppPassword = projectName + "_role";
        this.databaseSaUsername = "sa";
        this.databaseSaPassword = "";
        this.migraterConfig.setProjectNameForDefaults(projectName);
        this.codegenConfig.setProjectNameForDefaults(projectName);
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
        if (this.appTableAsSaUser == null) {
            this.appTableAsSaUser = this.makeDataSource(this.databaseName, this.databaseSaUsername, this.databaseSaPassword);
        }
        return this.appTableAsSaUser;
    }

    private DataSource getDataSourceForSystemTableAsSaUser() {
        if (this.systemTableAsSaUser == null) {
            this.systemTableAsSaUser = this.makeDataSource("postgres", this.databaseSaUsername, this.databaseSaPassword);
        }
        return this.systemTableAsSaUser;
    }

    private DataSource makeDataSource(String databaseName, String username, String password) {
        Reflection.forName("org.postgresql.Driver");
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost/" + databaseName);
        ds.setUser(username);
        ds.setPassword(password);
        ds.setMaxPoolSize(3);
        ds.setInitialPoolSize(1);
        return ds;
    }

}
