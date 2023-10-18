// Action file containing essential test actions
// TODO - Refactor into a library for minimal modifications

package com;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class SeleniumActions {
    Actions jsActions;
    Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final WebDriver driver;
    private final WebDriverWait wait;


    public SeleniumActions() {
        this.driver = RunCucumberTest.initWebDriver();
        this.wait = RunCucumberTest.initWait();
        jsActions = new Actions(driver);
    }

    public void navigateTo(String URL) {
        driver.get(URL);
        LOGGER.info("Navigating to " + URL);
    }

    public void click(By byElement) {
        wait.until(ExpectedConditions.elementToBeClickable(byElement));
        jsActions.moveToElement(driver.findElement(byElement));
        driver.findElement(byElement).click();
        LOGGER.info("Clicked the " + byElement + " button");
    }

    public void enterText(By byElement, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
        driver.findElement(byElement).clear();
        driver.findElement(byElement).sendKeys(text);
        LOGGER.info("Set text '" + text + "' into textbox " + byElement);
    }

    public String getText(By byElement) {
        wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
        String text = driver.findElement(byElement).getText();
        LOGGER.info("Retrived text '" + text + "' for element " + byElement);
        return text;

    }

    public String getFeatureBulletList(By byElement) {
        java.util.List<WebElement> bulletPoints = driver.findElement(byElement).findElements(By.tagName("li"));

        if (bulletPoints.isEmpty()) {
            LOGGER.info("No description was available for " + byElement);
            return "";
        }

        StringBuilder text = new StringBuilder();

        for (WebElement bulletPoint : bulletPoints) {
            text.append(bulletPoint.getText()).append("\n");
        }
        LOGGER.info("Feature-Bullet description = " + text);
        return text.toString();
    }

    public void takeScreenshot(String name) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);

            String destinationDirectory = "src/test/java/com/screenshots/";
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = name + "_" + timestamp + ".png";
            String destinationPath = destinationDirectory + fileName;

            File finalDestination = new File(destinationPath);
            FileUtils.copyFile(source, finalDestination);
            LOGGER.info("Succesfully captured a screenshot with name " + fileName + "and location " + finalDestination);

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Failed to take the screenshot");
        }
    }

    public String getURL(){
        String URL = driver.getCurrentUrl();
        LOGGER.info("Current URL " + URL);
        return URL;
    }
}