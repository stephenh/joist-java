package joist.codegen;

import joist.codegen.passes.Pass;
import joist.sourcegen.GDirectory;

/** Generates our domain objects from the database schema. */
public class Codegen {

  private final Config config;
  private final Schema schema;
  private final GDirectory outputCodegenDirectory;
  private final GDirectory outputSourceDirectory;

  /** @param saDataSource should be sa so we can see the information schema stuff */
  public Codegen(Config config, Schema schema) {
    this.config = config;
    this.schema = schema;
    this.outputCodegenDirectory = new GDirectory(config.getOutputCodegenDirectory());
    this.outputSourceDirectory = new GDirectory(config.getOutputSourceDirectory());
  }

  public void generate() {
    for (Pass<Codegen> pass : this.config.getCodegenPasses()) {
      pass.pass(this);
    }
  }

  public Schema getSchema() {
    return this.schema;
  }

  public Config getConfig() {
    return this.config;
  }

  public GDirectory getOutputCodegenDirectory() {
    return this.outputCodegenDirectory;
  }

  public GDirectory getOutputSourceDirectory() {
    return this.outputSourceDirectory;
  }

}
