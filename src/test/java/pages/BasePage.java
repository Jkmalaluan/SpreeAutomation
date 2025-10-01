package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class BasePage {
    protected Page page;

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
}