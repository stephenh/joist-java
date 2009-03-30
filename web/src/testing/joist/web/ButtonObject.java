package joist.web;

public class ButtonObject {

    protected final AbstractPageObject pageObject;
    protected final String id;

    public ButtonObject(AbstractPageObject pageObject, String id) {
        this.pageObject = pageObject;
        this.id = id;
    }

    public void click() {
        this.pageObject.click(this.id);
    }

}
