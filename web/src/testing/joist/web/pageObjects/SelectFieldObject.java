package joist.web.pageObjects;

import org.openqa.selenium.By;

public class SelectFieldObject extends AbstractElementObject {

    public SelectFieldObject(AbstractPageObject pageObject, String id) {
        super(pageObject, id);
    }

    public String get() {
        return this.pageObject.getValueOrNull(this.id);
    }

    public void select(int index) {
        this.pageObject.driver.findElement(By.id(this.id + "-" + index)).setSelected();
    }

}
