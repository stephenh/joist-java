package features.domain;

public enum CodeASize implements org.exigencecorp.domainobjects.Code {

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
        return org.exigencecorp.domainobjects.util.Codes.fromInt(CodeASize.values(), id);
    }

}
