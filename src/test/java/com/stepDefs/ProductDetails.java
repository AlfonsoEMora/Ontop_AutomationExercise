//Step definition files will be handled by feature, not by page

package com.stepDefs;

import com.ConfigurationManager;
import com.RunCucumberTest;
import com.SeleniumActions;
import com.pomFiles.HomePage;
import com.pomFiles.ProductDetailsPage;
import com.pomFiles.SearchPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class ProductDetails {
    private static final Map<String, String> sharedData = new HashMap<>();
    private final WebDriver driver;
    SeleniumActions actions = new SeleniumActions();

    HomePage homePage = new HomePage();
    SearchPage searchPage = new SearchPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();

    public ProductDetails() {
        this.driver = RunCucumberTest.initWebDriver();
    }

    @Given("I am on the Amazon home page")
    public void iAmOnTheAmazonHomePage() {
        actions.navigateTo(ConfigurationManager.getProperty("baseURL"));
    }

    @And("I search for the {string} product")
    public void iSearchForTheProduct(String product) {
        actions.enterText(homePage.txtSearchBox, product);
        actions.click(homePage.btnSearchIcon);
    }

    @And("I select the first entry in the search list")
    public void iSelectTheFirstEntryInTheSearchList() {
        actions.click(searchPage.btnFirstResult);
    }

    @Then("I verify that the product URL, title, price, rating, and description are displayed accurately on the details page")
    public void iVerifyThatTheProductURLTitlePriceRatingAndDescriptionAreDisplayedAccuratelyOnTheDetailsPage() {
        sharedData.put("productURL1", actions.getURL());
        sharedData.put("productTitle1", actions.getText(productDetailsPage.lblProductTitle));
        sharedData.put("productPrice1", actions.getText(productDetailsPage.lblProductPrice));
        sharedData.put("productRating1", actions.getText(productDetailsPage.lblProductRating));
        sharedData.put("productDescription1", actions.getFeatureBulletList(productDetailsPage.lblProductDesc));
        Assert.assertFalse(sharedData.get("productTitle1").isEmpty());
        Assert.assertFalse(sharedData.get("productPrice1").isEmpty());
        Assert.assertFalse(sharedData.get("productRating1").isEmpty());
        Assert.assertFalse(sharedData.get("productDescription1").isEmpty());
        actions.takeScreenshot(sharedData.get("productTitle1").replace(" ", ""));
    }

    @When("I go back to the search results for the same product")
    public void iGoBackToTheSearchResultsForTheSameProduct() {
        driver.navigate().back();
    }

    @And("I select the second entry in the search list")
    public void iSelectTheSecondEntryInTheSearchList() {
        actions.click(searchPage.btnSecondResult);
    }

    @Then("I verify that the second product detail page is different from the first one")
    public void iVerifyThatTheSecondProductDetailPageIsDifferentFromTheFirstOne() {
        sharedData.put("productURL2", actions.getURL());
        sharedData.put("productTitle2", actions.getText(productDetailsPage.lblProductTitle));
        sharedData.put("productPrice2", actions.getText(productDetailsPage.lblProductPrice));
        sharedData.put("productRating2", actions.getText(productDetailsPage.lblProductRating));
        sharedData.put("productDescription2", actions.getFeatureBulletList(productDetailsPage.lblProductDesc));
        Assert.assertNotEquals(sharedData.get("productURL1"), sharedData.get("productURL2"));
        Assert.assertNotEquals(sharedData.get("productTitle1"), sharedData.get("productTitle2"));
        Assert.assertNotEquals(sharedData.get("productPrice1"), sharedData.get("productPrice2"));
        Assert.assertNotEquals(sharedData.get("productRating1"), sharedData.get("productRating2"));
        Assert.assertNotEquals(sharedData.get("productDescription1"), sharedData.get("productDescription2"));
        actions.takeScreenshot(sharedData.get("productTitle2").replace(" ", ""));
    }
}
