package features.domain;

import joist.domain.Code;

public enum CodeASize implements Code {

  ONE(1l, "ONE", "One"),
  TWO(2l, "TWO", "Two");

  private Long id;
  private String code;
  private String name;

  private CodeASize(Long id, String code, String name) {
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

  public static CodeASize fromId(long id) {
    return joist.domain.util.Codes.fromLong(CodeASize.values(), id);
  }

  public static CodeASize fromCode(String code) {
    return joist.domain.util.Codes.fromCode(CodeASize.values(), code);
  }

}
