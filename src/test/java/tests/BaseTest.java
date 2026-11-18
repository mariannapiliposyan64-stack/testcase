package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Constants;
import utils.DriverFactory;
import org.openqa.selenium.WebDriver;

/**
 * Base test class. Every test class extends this to get driver lifecycle management.
 */
public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get(Constants.BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
