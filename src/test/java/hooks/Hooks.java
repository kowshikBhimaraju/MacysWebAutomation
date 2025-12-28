package hooks;

import framework.core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import support.TestContext;

import java.io.IOException;

public class Hooks {

    private final TestContext testContext;

    /*As testContext variable is a final variable java expects it to be initialized at the point of declaration
     * or inside any constructor*/

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before
    public void setUp() throws IOException {
        WebDriver driver = DriverFactory.create();// returns driver session
        testContext.setDriver(driver);
    }

    @After
    public void tearDown() {
        WebDriver driver = testContext.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
