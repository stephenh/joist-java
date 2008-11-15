package org.exigencecorp.domainobjects;

import javax.sql.DataSource;

import org.exigencecorp.domainobjects.codegen.Codegen;
import org.exigencecorp.domainobjects.codegen.CodegenConfig;
import org.exigencecorp.domainobjects.migrations.Migrater;
import org.exigencecorp.domainobjects.migrations.MigraterConfig;
import org.exigencecorp.domainobjects.migrations.Resetter;
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
    public DataSource appDataSource = null;
    public DataSource systemDataSource = null;

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
        new Resetter(this.getSystemDatasource(), this.databaseName, this.databaseAppUsername, this.databaseAppPassword).reset();
    }

    public void migrateDatabase() {
        new Migrater(this.migraterConfig, this.getAppDatasource()).performMigrations();
    }

    public void codegen() {
        new Codegen(this.codegenConfig, this.getAppDatasource()).generate();
    }

    public DataSource getSystemDatasource() {
        if (this.systemDataSource == null) {
            Reflection.forName("org.postgresql.Driver");
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setJdbcUrl("jdbc:postgresql://localhost/postgres");
            ds.setUser(this.databaseSaUsername);
            ds.setPassword(this.databaseSaPassword);
            ds.setMaxPoolSize(3);
            ds.setInitialPoolSize(1);
            this.systemDataSource = ds;
        }
        return this.systemDataSource;
    }

    public DataSource getAppDatasource() {
        if (this.appDataSource == null) {
            Reflection.forName("org.postgresql.Driver");
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setJdbcUrl("jdbc:postgresql://localhost/" + this.databaseName);
            ds.setUser(this.databaseAppUsername);
            ds.setPassword(this.databaseAppPassword);
            ds.setMaxPoolSize(3);
            ds.setInitialPoolSize(1);
            this.appDataSource = ds;
        }
        return this.appDataSource;
    }

}
