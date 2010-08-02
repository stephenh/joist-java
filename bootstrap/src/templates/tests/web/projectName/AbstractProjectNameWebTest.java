package @projectName@;

import joist.web.Jetty;
import @projectName@.webapp.pageObjects.IndexPageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class Abstract@ProjectName@WebTest extends Abstract@ProjectName@IntegrationTest {

    protected static WebDriver driver;

    public void setUp() throws Exception {
        super.setUp();
        if (driver == null) {
            System.setProperty("webdriver.firefox.useExisting", "true");
            driver = new FirefoxDriver();
        }
        Jetty.startIfNotRunning();
    }

    protected IndexPageObject startAtIndexPage() {
        IndexPageObject p = new IndexPageObject(driver);
        p.open();
        p.clearCookies();
        p.open();
        return p;
    }

}
