package joist.domain.codegen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import joist.domain.codegen.dtos.Entity;
import joist.domain.codegen.passes.FindCodeValuesPass;
import joist.domain.codegen.passes.FindForeignKeysPass;
import joist.domain.codegen.passes.FindManyToManyPropertiesPass;
import joist.domain.codegen.passes.FindPrimitivePropertiesPass;
import joist.domain.codegen.passes.FindTablesPass;
import joist.domain.codegen.passes.GenerateAliasesPass;
import joist.domain.codegen.passes.GenerateCodesPass;
import joist.domain.codegen.passes.GenerateDomainClassIfNotExistsPass;
import joist.domain.codegen.passes.GenerateDomainCodegenPass;
import joist.domain.codegen.passes.GenerateFlushFunction;
import joist.domain.codegen.passes.GenerateQueriesCodegenPass;
import joist.domain.codegen.passes.GenerateQueriesIfNotExistsPass;
import joist.domain.codegen.passes.GenerateSchemaHash;
import joist.domain.codegen.passes.OutputPass;
import joist.domain.codegen.passes.Pass;
import joist.sourcegen.GDirectory;
import joist.util.Copy;

/** Generates our domain objects from the database schema. */
public class Codegen {

    private final CodegenConfig config;
    private final DataSource dataSource;
    private final InformationSchemaWrapper informationSchema;
    private final Map<String, Entity> entities = new LinkedHashMap<String, Entity>();
    private final GDirectory outputCodegenDirectory;
    private final GDirectory outputSourceDirectory;
    private final List<String> codeTables;
    private final List<String> manyToManyTables;

    /** @param saDataSource should be sa so we can see the information schema stuff */
    public Codegen(DataSource saDataSource, CodegenConfig config) {
        this.config = config;
        this.dataSource = saDataSource;
        this.outputCodegenDirectory = new GDirectory(config.getOutputCodegenDirectory());
        this.outputSourceDirectory = new GDirectory(config.getOutputSourceDirectory());
        this.informationSchema = new InformationSchemaWrapper(saDataSource);
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

}
