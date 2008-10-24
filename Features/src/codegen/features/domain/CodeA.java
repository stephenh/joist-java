package features.domain;

public enum CodeA {

    ONE(0, "ONE", "One");

    private Integer id;
    private String code;
    private String name;

    private CodeA(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId(Integer id) {
        return this.id;
    }

    public String getCode(String code) {
        return this.code;
    }

    public String getName(String name) {
        return this.name;
    }

}
