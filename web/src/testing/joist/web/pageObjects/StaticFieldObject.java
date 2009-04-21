package joist.web.pageObjects;

public class StaticFieldObject {

    protected final AbstractPageObject pageObject;
    protected final String id;

    public StaticFieldObject(AbstractPageObject pageObject, String id) {
        this.pageObject = pageObject;
        this.id = id;
    }

    public String get() {
        return this.pageObject.getTextOrNull(this.id);
    }

}
