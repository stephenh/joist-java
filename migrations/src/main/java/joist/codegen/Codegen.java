package joist.codegen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import joist.codegen.dtos.Entity;
import joist.codegen.passes.Pass;
import joist.domain.util.ConnectionSettings;
import joist.sourcegen.GDirectory;

/** Generates our domain objects from the database schema. */
public class Codegen {

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
  public Codegen(ConnectionSettings appDbSettings, DataSource saDataSource, CodegenConfig config) {
    this.config = config;
    this.appDbSettings = appDbSettings;
    this.dataSource = saDataSource;
    this.outputCodegenDirectory = new GDirectory(config.getOutputCodegenDirectory());
    this.outputSourceDirectory = new GDirectory(config.getOutputSourceDirectory());
    this.informationSchema = new InformationSchemaWrapper(appDbSettings.db, appDbSettings.schemaName, saDataSource);
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

}
