package joist.web;

public class TextFieldObject {

    protected final AbstractPageObject pageObject;
    protected final String id;

    public TextFieldObject(AbstractPageObject pageObject, String id) {
        this.pageObject = pageObject;
        this.id = id;
    }

    public String get() {
        return this.pageObject.getValueOrNull(this.id);
    }

    public void type(String value) {
        this.pageObject.type(this.id, value);
    }

}
