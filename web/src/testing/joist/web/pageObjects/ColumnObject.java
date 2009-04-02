package joist.web.pageObjects;

public class ColumnObject {

    protected final AbstractPageObject pageObject;
    protected final String id;

    public ColumnObject(AbstractPageObject pageObject, String id) {
        this.pageObject = pageObject;
        this.id = id;
    }

    public String get(int index) {
        return this.pageObject.getTextOrNull(this.id + "-" + index);
    }

    public void click(int index) {
        this.pageObject.click(this.id + "-" + index + "-link");
    }

}
