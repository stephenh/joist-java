package features.domain;

import joist.domain.Code;

public enum CodeAColor implements Code {

    BLUE(1, "BLUE", "Blue"),
    GREEN(2, "GREEN", "Green");

    private Integer id;
    private String code;
    private String name;

    private CodeAColor(Integer id, String code, String name) {
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

    public static CodeAColor fromId(Integer id) {
        return joist.domain.util.Codes.fromInt(CodeAColor.values(), id);
    }

}
