package pages;

import com.microsoft.playwright.Page;

public class PaymentPage extends BasePage {
    private final String payNowButton = "//button[@id='checkout-payment-submit']";
    private final String orderNumberString = "//p[contains(text(), 'Order')]/strong";
    private final String orderConfirmationMessage = "//h5[normalize-space()='Your order is confirmed!']";

    public PaymentPage(Page page) {
        super(page);
    }

    public void enterCardDetails(String cardNum, String expiry, String cvc) {
        
        page.waitForSelector("iframe[title*='Secure payment input frame']");
        var frameLocator = page.frameLocator("iframe[title*='Secure payment input frame']");
        
        // Fill in card details
        frameLocator.locator("#Field-numberInput").fill(cardNum);
        frameLocator.locator("#Field-expiryInput").fill(expiry);
        frameLocator.locator("#Field-cvcInput").fill(cvc);
    }

    public void clickPayNow() {
        waitForElement(page.locator(payNowButton));
        page.keyboard().press("Enter");
    }

    public boolean isSuccessMessageDisplayed() {
       waitForElement(page.locator(orderConfirmationMessage));
       return page.isVisible(orderConfirmationMessage);
    }

    public String getOrderNumber() {
        waitForElement(page.locator(orderNumberString));
        return getElementText(page.locator(orderNumberString));
    }
}