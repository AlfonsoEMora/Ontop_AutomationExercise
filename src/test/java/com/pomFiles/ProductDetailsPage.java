//Page Object Model for the ProductDetailsPage

package com.pomFiles;

import org.openqa.selenium.By;

public class ProductDetailsPage {
    public By lblProductTitle = By.id("productTitle");

    public By lblProductPrice = By.xpath("//div[@id='corePrice_feature_div']//span[@class='a-price-whole']");

    public By lblProductRating = By.xpath("//*[@id='acrPopover']/span[1]/a/span");

    public By lblProductDesc = By.id("feature-bullets");
}
