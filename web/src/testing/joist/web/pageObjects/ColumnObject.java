package joist.web.pageObjects;

public class ColumnObject extends AbstractElementObject {

    public ColumnObject(AbstractPageObject pageObject, String id) {
        super(pageObject, id);
    }

    public String get(int index) {
        return this.pageObject.getTextOrNull(this.id + "-" + index);
    }

    public void click(int index) {
        this.pageObject.click(this.id + "-" + index + "-link");
    }

}
