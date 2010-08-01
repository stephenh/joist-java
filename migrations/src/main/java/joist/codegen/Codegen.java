package joist.codegen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import joist.codegen.dtos.Entity;
import joist.codegen.passes.FindCodeValuesPass;
import joist.codegen.passes.FindForeignKeysPass;
import joist.codegen.passes.FindManyToManyPropertiesPass;
import joist.codegen.passes.FindPrimitivePropertiesPass;
import joist.codegen.passes.FindTablesPass;
import joist.codegen.passes.GenerateAliasesPass;
import joist.codegen.passes.GenerateCodesPass;
import joist.codegen.passes.GenerateDomainClassIfNotExistsPass;
import joist.codegen.passes.GenerateDomainCodegenPass;
import joist.codegen.passes.GenerateFlushFunction;
import joist.codegen.passes.GenerateQueriesCodegenPass;
import joist.codegen.passes.GenerateQueriesIfNotExistsPass;
import joist.codegen.passes.GenerateSchemaHash;
import joist.codegen.passes.OutputPass;
import joist.codegen.passes.Pass;
import joist.domain.orm.Db;
import joist.domain.util.ConnectionSettings;
import joist.sourcegen.GDirectory;
import joist.util.Copy;

/** Generates our domain objects from the database schema. */
public class Codegen {

    private final Db db;
    private final CodegenConfig config;
    private final ConnectionSettings appDbSettings;
    private final DataSource dataSource;
    private final InformationSchemaWrapper informationSchema;
    private final Map<String, Entity> entities = new LinkedHashMap<String, Entity>();
    private final GDirectory outputCodegenDirectory;
    private final GDirectory outputSourceDirectory;
    private final List<String> codeTables;
    private final List<String> manyToManyTables;

    /** @param saDataSource should be sa so we can see the information schema stuff */
    public Codegen(Db db, ConnectionSettings appDbSettings, DataSource saDataSource, CodegenConfig config) {
        this.db = db;
        this.config = config;
        this.appDbSettings = appDbSettings;
        this.dataSource = saDataSource;
        this.outputCodegenDirectory = new GDirectory(config.getOutputCodegenDirectory());
        this.outputSourceDirectory = new GDirectory(config.getOutputSourceDirectory());
        this.informationSchema = new InformationSchemaWrapper(db, appDbSettings.databaseName, saDataSource);
        this.codeTables = this.informationSchema.getCodeTables();
        this.manyToManyTables = this.informationSchema.getManyToManyTables();
    }

    public void generate() {
        for (Pass pass : this.getPasses()) {
            pass.pass(this);
        }
    }

    public List<Pass> getPasses() {
        return Copy.list(
            new FindTablesPass(),
            new FindPrimitivePropertiesPass(),
            new FindForeignKeysPass(),
            new FindCodeValuesPass(),
            new FindManyToManyPropertiesPass(),
            new GenerateCodesPass(),
            new GenerateDomainClassIfNotExistsPass(),
            new GenerateDomainCodegenPass(),
            new GenerateQueriesIfNotExistsPass(),
            new GenerateQueriesCodegenPass(),
            new GenerateAliasesPass(),
            new GenerateFlushFunction(),
            new GenerateSchemaHash(),
            new OutputPass());
    }

    public Map<String, Entity> getEntities() {
        return this.entities;
    }

    public List<InformationSchemaColumn> getColumns() {
        return this.informationSchema.getColumns();
    }

    public int getSchemaHashCode() {
        return this.informationSchema.getSchemaHashCode();
    }

    public CodegenConfig getConfig() {
        return this.config;
    }

    public Entity getEntity(InformationSchemaColumn column) {
        return this.entities.get(column.tableName);
    }

    public Entity getEntity(String tableName) {
        return this.entities.get(tableName);
    }

    public boolean isCodeTable(InformationSchemaColumn column) {
        return this.codeTables.contains(column.tableName);
    }

    public boolean isManyToManyTable(InformationSchemaColumn column) {
        return this.manyToManyTables.contains(column.tableName);
    }

    public GDirectory getOutputCodegenDirectory() {
        return this.outputCodegenDirectory;
    }

    public GDirectory getOutputSourceDirectory() {
        return this.outputSourceDirectory;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public ConnectionSettings getAppDbSettings() {
        return this.appDbSettings;
    }

    public Db getDb() {
        return this.db;
    }

}
