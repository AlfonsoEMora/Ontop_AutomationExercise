//Page Object Model for the SearchPage

package com.pomFiles;

import org.openqa.selenium.By;

public class SearchPage {
    public By btnFirstResult = By.xpath("(//div[@data-component-type='s-search-result']//img)[1]");

    public By btnSecondResult = By.xpath("(//div[@data-component-type='s-search-result']//img)[2]");
}
