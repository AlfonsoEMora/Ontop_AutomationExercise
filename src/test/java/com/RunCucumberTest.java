// TestRunner file used to orchestrate the test execution

package com;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.time.Duration;
import java.util.logging.Logger;

@CucumberOptions(
        features = "src/test/java/com/features",
        glue = "com.stepDefs",
        tags = "@ProductDetails",
        plugin = {"pretty"},
        monochrome = true
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
    // ThreadLocal is used to manage WebDriver for parallel test execution
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    private static final boolean isParallel = false;    // Used to enable parallel execution

    // Initializes logging and stores log files in /logs directory.
    static {
        String path = RunCucumberTest.class.getClassLoader().getResource("logging.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);
    }

    Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // Methods to obtain the explicit wait and driver objects.
    public static WebDriver initWebDriver() {
        return driver.get();
    }
    public static WebDriverWait initWait() {
        return wait.get();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupWebDriver() {
        if (isParallel && driver.get() != null) {
            return; // Reuse the existing driver if running in parallel
        }

        ChromeOptions options = new ChromeOptions();
        WebDriverManager.chromedriver().setup();

        if (Boolean.parseBoolean(ConfigurationManager.getProperty("headless"))) {
            options.addArguments("--headless");
            LOGGER.config("Setting up headless Chrome");
        }
        options.addArguments(ConfigurationManager.getProperty("chromeOptions"));
        LOGGER.config("Starting up Chrome Driver");

        WebDriver newDriver = new ChromeDriver(options);
        WebDriverWait newWait = new WebDriverWait(newDriver, Duration.ofSeconds(Long.parseLong(ConfigurationManager.getProperty("explicitWait"))));

        driver.set(newDriver);
        wait.set(newWait);
    }

    @AfterMethod(alwaysRun = true)
    public void afterScenario(ITestResult result) {
        if (driver.get() != null) {
            driver.get().quit();
            LOGGER.config("Killed Chrome Driver");
        }
        driver.remove();
        wait.remove();
    }

    @Override
    @DataProvider(parallel = isParallel)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}