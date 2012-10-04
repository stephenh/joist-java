package joist.codegen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joist.codegen.dtos.Entity;
import joist.codegen.passes.Pass;

/** Populates objects with the metadata for the database schema. */
public class Schema {

  private final Config config;
  private final InformationSchemaWrapper informationSchema;
  private final Map<String, Entity> entities = new LinkedHashMap<String, Entity>();
  private final List<String> codeTables;
  private final List<String> manyToManyTables;

  /** @param saDataSource should be sa so we can see the information schema stuff */
  public Schema(Config config) {
    this.config = config;
    this.informationSchema = new InformationSchemaWrapper(config.db, config.dbAppUserSettings.schemaName, config.dbAppSaSettings.getDataSource());
    this.codeTables = this.informationSchema.getCodeTables();
    this.manyToManyTables = this.informationSchema.getManyToManyTables();
  }

  public void populate() {
    for (Pass<Schema> pass : this.config.getDataPasses()) {
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

}
