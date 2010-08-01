package features.domain;

import joist.domain.Code;

public enum CodeASize implements Code {

    ONE(1, "ONE", "One"),
    TWO(2, "TWO", "Two");

    private Integer id;
    private String code;
    private String name;

    private CodeASize(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return this.id;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static CodeASize fromId(Integer id) {
        return joist.domain.util.Codes.fromInt(CodeASize.values(), id);
    }

    public static CodeASize fromCode(String code) {
        return joist.domain.util.Codes.fromCode(CodeASize.values(), code);
    }

}
