package features.domain;

import joist.domain.Code;

public enum CodeAColor implements Code {

  BLUE(1l, "BLUE", "Blue"),
  GREEN(2l, "GREEN", "Green");

  private Long id;
  private String code;
  private String name;

  private CodeAColor(Long id, String code, String name) {
    this.id = id;
    this.code = code;
    this.name = name;
  }

  public Long getId() {
    return this.id;
  }

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  public static CodeAColor fromId(long id) {
    return joist.domain.util.Codes.fromLong(CodeAColor.values(), id);
  }

  public static CodeAColor fromCode(String code) {
    return joist.domain.util.Codes.fromCode(CodeAColor.values(), code);
  }

}
