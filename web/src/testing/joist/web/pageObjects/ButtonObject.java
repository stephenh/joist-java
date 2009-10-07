package joist.web.pageObjects;

public class ButtonObject extends AbstractElementObject {

    public ButtonObject(AbstractPageObject pageObject, String id) {
        super(pageObject, id);
    }

    public void click() {
        this.getElement().click();
    }

}
