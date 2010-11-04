package joist.codegen.dtos;

import joist.util.Inflector;

public class CodeValue {

  public String id;
  public String code;
  public String name;

  public CodeValue(String id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public String getNameCamelCased() {
    return Inflector.capitalize(Inflector.camelize(this.code.toLowerCase()));
  }

  public String getEnumName() {
    if (this.code.matches("^[0-9].*")) {
      return "_" + this.code;
    }
    return this.code;
  }

}
