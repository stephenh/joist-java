package @projectName@.webapp.pageObjects;

import org.openqa.selenium.WebDriver;

public class IndexPageObject extends Abstract@ProjectName@PageObject {

    public IndexPageObject(WebDriver driver) {
        super(driver);
    }

    public void open() {
        this.open("/index.htm");
    }

}
