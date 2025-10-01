package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.TimeoutError;

public class BasePage {
    protected Page page;
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static final int DEFAULT_TIMEOUT_MS = 5000;
    private static final int DEFAULT_DELAY_MS = 1000;

    public BasePage(Page page) {
        this.page = page;
    }

    protected String getElementText(Locator locator) {
        locator.waitFor();
        return locator.innerText();
    }

    protected String getElementValue(Locator locator) {
        locator.waitFor();
        return locator.inputValue();
    }

    protected void waitForElement(Locator locator) {
        locator.waitFor();
    }

    protected void clickElement(String selector) {
        page.click(selector);
    }

    protected void fillElement(String selector, String value) {
        page.fill(selector, value);
    }

    protected void selectElement(String selector, String value) {
        Locator locator = page.locator(selector);
        waitForElement(locator);
        page.selectOption(selector, value);
    }

    protected void clickElementWithValidation(String clickSelector, String validationSelector) {
        clickElementWithValidation(clickSelector, validationSelector, DEFAULT_MAX_ATTEMPTS, DEFAULT_TIMEOUT_MS, DEFAULT_DELAY_MS);
    }

    protected void clickElementWithValidation(String clickSelector, String validationSelector, 
                                            int maxAttempts, int timeoutMs, int delayMs) {
        int attempts = 0;
        TimeoutError lastException = null;

        while (attempts < maxAttempts) {
            try {
                // Click the element
                Locator clickLocator = page.locator(clickSelector);
                clickLocator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
                if (clickLocator.isVisible()) {
                    clickLocator.click();
                    
                    // Check if validation element appears
                    Locator validationLocator = page.locator(validationSelector);
                    try {
                        validationLocator.waitFor(new Locator.WaitForOptions().setTimeout(timeoutMs));
                        if (validationLocator.isVisible()) {
                            return; // Success - validation element is visible
                        }
                    } catch (TimeoutError e) {
                        // Validation element not found, will retry
                        lastException = e;
                    }
                }
            } catch (TimeoutError e) {
                lastException = e;
            }
            
            attempts++;
            if (attempts < maxAttempts) {
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while waiting between retries", ie);
                }
            }
        }
        
        throw new RuntimeException("Failed to click element or validation element did not appear after " 
            + maxAttempts + " attempts", lastException);
    }
}