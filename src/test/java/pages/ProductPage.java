package pages;

import com.microsoft.playwright.Page;

public class ProductPage extends BasePage {
    private final String searchButton = "//button[@id='open-search']";
    private final String searchBox = "input[id='q']";
    private final String productCard = "//a[.//h3[normalize-space()='%s'] and not(.//div[@data-plp-variant-picker-target='featuredImageContainer'][contains(@style, 'height: 0px')])]";
   


    public ProductPage(Page page) {
        super(page);
    }

    public void enterProductName(String productName) {
        fillElement(searchBox, productName);
        page.keyboard().press("Enter");
    }
    
    public void clickSearchButton() {
        clickElement(searchButton);
    }

    public void clickProduct(String productName) {
        // Wait for the search results to load
        page.waitForLoadState();
        
        // Use product title instead of image
        String productSelector = String.format(productCard, productName);
        waitForElement(page.locator(productSelector));
        clickElement(productSelector);
        
        // Wait for product page to plad
        page.waitForLoadState();
    }
}
