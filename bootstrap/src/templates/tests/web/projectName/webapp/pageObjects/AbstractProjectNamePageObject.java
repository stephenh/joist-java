package @projectName@.webapp.pageObjects;

import joist.domain.uow.UoW;
import joist.web.pageObjects.AbstractPageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Abstract@ProjectName@PageObject extends AbstractPageObject {

    public Abstract@ProjectName@PageObject(WebDriver driver) {
        super(driver);
    }

    @Override
    public void click(String id) {
        UoW.commitAndReOpen();
        super.click(id);
    }

    @Override
    public void open(String path) {
        UoW.commitAndReOpen();
        this.driver.get(this.getBasePath() + path);
    }

    public String getMessage(int i) {
        return this.driver.findElement(By.id("messages." + i)).getText();
    }

    public String getMessages() {
        return this.driver.findElement(By.id("messages")).getText();
    }

    public void clearCookies() {
        this.driver.manage().deleteAllCookies();
    }

}
