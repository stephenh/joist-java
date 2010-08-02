package joist.codegen.dtos;

public class CodeValue {

  public String id;
  public String code;
  public String name;

  public CodeValue(String id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public String getEnumName() {
    if (this.code.matches("^[0-9].*")) {
      return "_" + this.code;
    }
    return this.code;
  }

}
