package joist.codegen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joist.codegen.dtos.Entity;
import joist.codegen.passes.Pass;
import joist.sourcegen.GDirectory;

/** Generates our domain objects from the database schema. */
public class Codegen {

  private final Config config;
  private final InformationSchemaWrapper informationSchema;
  private final Map<String, Entity> entities = new LinkedHashMap<String, Entity>();
  private final GDirectory outputCodegenDirectory;
  private final GDirectory outputSourceDirectory;
  private final List<String> codeTables;
  private final List<String> manyToManyTables;

  /** @param saDataSource should be sa so we can see the information schema stuff */
  public Codegen(Config config) {
    this.config = config;
    this.outputCodegenDirectory = new GDirectory(config.getOutputCodegenDirectory());
    this.outputSourceDirectory = new GDirectory(config.getOutputSourceDirectory());
    this.informationSchema = new InformationSchemaWrapper(config.db, config.dbAppUserSettings.schemaName, config.dbAppSaSettings.getDataSource());
    this.codeTables = this.informationSchema.getCodeTables();
    this.manyToManyTables = this.informationSchema.getManyToManyTables();
  }

  public void generate() {
    for (Pass pass : this.config.getPasses()) {
      pass.pass(this);
    }
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

  public Config getConfig() {
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

}
