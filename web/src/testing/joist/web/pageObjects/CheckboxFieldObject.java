package joist.web.pageObjects;

public class CheckboxFieldObject extends AbstractElementObject {

    public CheckboxFieldObject(AbstractPageObject pageObject, String id) {
        super(pageObject, id);
    }

    public boolean checked() {
        return this.getElement().isSelected();
    }

    public void check() {
        this.getElement().setSelected();
    }

}
