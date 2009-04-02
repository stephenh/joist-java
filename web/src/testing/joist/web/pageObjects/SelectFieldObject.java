package joist.web.pageObjects;

import org.openqa.selenium.By;

public class SelectFieldObject {

    protected final AbstractPageObject pageObject;
    protected final String id;

    public SelectFieldObject(AbstractPageObject pageObject, String id) {
        this.pageObject = pageObject;
        this.id = id;
    }

    public String get() {
        return this.pageObject.getValueOrNull(this.id);
    }

    public void select(int index) {
        this.pageObject.driver.findElement(By.id(this.id + "-" + index)).setSelected();
    }

}
