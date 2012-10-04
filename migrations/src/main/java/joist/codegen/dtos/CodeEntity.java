package joist.codegen.dtos;

import java.util.ArrayList;
import java.util.List;

import joist.codegen.Config;

/** Represents tables that generated as enums but are still foreign keys in the database. */
public class CodeEntity extends Entity {

  private List<CodeValue> codes = new ArrayList<CodeValue>();

  public CodeEntity(Config config, String tableName) {
    super(config, tableName);
  }

  @Override
  public boolean isCodeEntity() {
    return true;
  }

  public void addCode(String id, String code, String name) {
    this.codes.add(new CodeValue(id, code, name));
  }

  public List<CodeValue> getCodes() {
    return this.codes;
  }

}
