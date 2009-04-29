package joist.web.pageObjects;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public abstract class AbstractPageObject {

    protected final WebDriver driver;

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void type(String id, String value) {
        this.driver.findElement(By.id(id)).clear();
        this.driver.findElement(By.id(id)).sendKeys(value);
    }

    public void click(String id) {
        this.driver.findElement(By.id(id)).click();
    }

    public String getValueOrNull(String id) {
        try {
            return this.driver.findElement(By.id(id)).getValue();
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    public String getTextOrNull(String id) {
        try {
            return this.driver.findElement(By.id(id)).getText();
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    public void clearCookies() {
        this.driver.manage().deleteAllCookies();
    }

    protected void openWithBasePath(String path) {
        this.driver.get(this.getBasePath() + path);
    }

    protected String getBasePath() {
        return "http://localhost:8080";
    }

    protected String getCurrentPath() {
        return StringUtils.removeStart(this.driver.getCurrentUrl(), this.getBasePath());
    }

    public String getPageSource() {
        return this.driver.getPageSource();
    }

}
