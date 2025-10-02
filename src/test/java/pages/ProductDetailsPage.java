package pages;

import com.microsoft.playwright.Page;

public class ProductDetailsPage extends BasePage {
    // Locators
    private final String sizeOptionButton = "//button[contains(@class, 'dropdown-button')]";
    private final String sizeOptionDropdown = "//label[@for='product-option-259-1-1']";
    private final String addToCartButton = "button.add-to-cart-button:visible";
    private final String sizeOptionLocator = "//p[contains(text(),'%s')]";
    
    // Locators for order details
    private final String orderNameLocator = "//a[@class='font-semibold text-text']";
    private final String orderQuantityLocator = "//input[@id='line_item_quantity']";
    private final String orderTotalLocator = "div.mb-2.text-sm span";
    private final String checkoutButton = "//a[normalize-space()='Checkout']";

    public ProductDetailsPage(Page page) {
        super(page);
    }

    public void selectSizeOption(String sizeOption) {
        String specificSizeLocator = String.format(sizeOptionLocator, sizeOption);
        
        page.waitForLoadState();
        waitForElement(page.locator(sizeOptionButton));
        clickElementWithValidation(sizeOptionButton,specificSizeLocator);
        waitForElement(page.locator(sizeOptionDropdown));
        clickElement(sizeOptionDropdown);
        waitForElement(page.locator(specificSizeLocator)); 
        clickElement(specificSizeLocator);
    }

    public OrderDetails getOrderDetails() {
        return new OrderDetails(
            getElementText(page.locator(orderNameLocator)),
            getElementValue(page.locator(orderQuantityLocator)),
            getElementText(page.locator(orderTotalLocator))
        );
    }

    public void clickCheckOUt() {
        waitForElement(page.locator(checkoutButton));
        clickElement(checkoutButton);
    }   

    public void clickAddToCart() {
        waitForElement(page.locator(addToCartButton));
        clickElementWithValidation(addToCartButton, checkoutButton);
    }
}