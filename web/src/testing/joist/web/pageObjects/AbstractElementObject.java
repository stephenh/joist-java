package joist.web.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class AbstractElementObject {

    protected final AbstractPageObject pageObject;
    protected final String id;

    public AbstractElementObject(AbstractPageObject pageObject, String id) {
        this.pageObject = pageObject;
        this.id = id;
    }

    protected WebElement getElement() {
        return this.pageObject.driver.findElement(By.id(this.id));
    }

}
