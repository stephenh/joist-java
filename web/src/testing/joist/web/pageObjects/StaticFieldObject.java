package joist.web.pageObjects;

public class StaticFieldObject extends AbstractElementObject {

    public StaticFieldObject(AbstractPageObject pageObject, String id) {
        super(pageObject, id);
    }

    public String get() {
        return this.pageObject.getTextOrNull(this.id);
    }

}
