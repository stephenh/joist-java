package org.exigencecorp.domainobjects.codegen;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.exigencecorp.domainobjects.codegen.dtos.Entity;
import org.exigencecorp.domainobjects.codegen.passes.FindCodeValuesPass;
import org.exigencecorp.domainobjects.codegen.passes.FindForeignKeysPass;
import org.exigencecorp.domainobjects.codegen.passes.FindManyToManyPropertiesPass;
import org.exigencecorp.domainobjects.codegen.passes.FindPrimitivePropertiesPass;
import org.exigencecorp.domainobjects.codegen.passes.FindTablesPass;
import org.exigencecorp.domainobjects.codegen.passes.GenerateAliasesPass;
import org.exigencecorp.domainobjects.codegen.passes.GenerateCodesPass;
import org.exigencecorp.domainobjects.codegen.passes.GenerateDomainClassIfNotExistsPass;
import org.exigencecorp.domainobjects.codegen.passes.GenerateDomainCodegenPass;
import org.exigencecorp.domainobjects.codegen.passes.GenerateFlushFunction;
import org.exigencecorp.domainobjects.codegen.passes.OutputPass;
import org.exigencecorp.domainobjects.codegen.passes.Pass;
import org.exigencecorp.gen.GDirectory;
import org.exigencecorp.util.Copy;

/** Generates our domain objects from the database schema. */
public class Codegen {

    private final CodegenConfig config;
    private final InformationSchemaWrapper informationSchema;
    private final Map<String, Entity> entities = new LinkedHashMap<String, Entity>();
    private final GDirectory outputCodegenDirectory;
    private final GDirectory outputSourceDirectory;
    private Set<String> codeTables = new HashSet<String>();
    private List<InformationSchemaColumn> columns;

    public Codegen(CodegenConfig config) {
        this.config = config;
        this.outputCodegenDirectory = new GDirectory(config.getOutputCodegenDirectory());
        this.outputSourceDirectory = new GDirectory(config.getOutputSourceDirectory());
        this.informationSchema = new InformationSchemaWrapper(this.config.getDataSource());
    }

    public void generate() {
        this.fillInRows();
        for (Pass pass : this.getPasses()) {
            pass.pass(this);
        }
    }

    private void fillInRows() {
        this.columns = this.informationSchema.getColumnMetaData();
        for (InformationSchemaColumn column : this.columns) {
            if (this.informationSchema.isCodeTable(column.tableName)) {
                this.codeTables.add(column.tableName);
            }
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
            new GenerateAliasesPass(),
            new GenerateFlushFunction(),
            new OutputPass());
    }

    public Map<String, Entity> getEntities() {
        return this.entities;
    }

    public List<InformationSchemaColumn> getColumns() {
        return this.columns;
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

    public GDirectory getOutputCodegenDirectory() {
        return this.outputCodegenDirectory;
    }

    public GDirectory getOutputSourceDirectory() {
        return this.outputSourceDirectory;
    }

}
