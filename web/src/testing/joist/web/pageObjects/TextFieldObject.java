package joist.web.pageObjects;

public class TextFieldObject extends AbstractElementObject {

    public TextFieldObject(AbstractPageObject pageObject, String id) {
        super(pageObject, id);
    }

    public String get() {
        return this.pageObject.getValueOrNull(this.id);
    }

    public void type(String value) {
        this.pageObject.type(this.id, value);
    }

}
