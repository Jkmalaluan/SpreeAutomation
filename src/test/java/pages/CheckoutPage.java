package pages;

import com.microsoft.playwright.Page;

public class CheckoutPage extends BasePage {
    // Shipping form locators
    private final String shippingAdd = "//select[@id='order_ship_address_attributes_country_id']";
    private final String firstName = "//input[@id='order_ship_address_attributes_firstname']";
    private final String lastName = "//input[@id='order_ship_address_attributes_lastname']";
    private final String address1 = "//input[@id='order_ship_address_attributes_address1']";
    private final String city = "//input[@id='order_ship_address_attributes_city']";
    private final String postalCode = "//input[@id='order_ship_address_attributes_zipcode']";
    private final String continueButton = "//button[normalize-space()='Save and Continue']";
    private final String shipmentOption = "//div[@class='shipment']";

    public CheckoutPage(Page page) {
        super(page);
    }

    public void selectCountry(String country) {
        selectElement(shippingAdd, country);
    }

    public void enterFirstName(String fname) {
        waitForElement(page.locator(firstName));
        fillElement(firstName, fname);
    }

    public void enterLastName(String lname) {
        waitForElement(page.locator(lastName));
        fillElement(lastName, lname);
    }

    public void enterAddress(String addr) {
        waitForElement(page.locator(address1));
        fillElement(address1, addr);
    }

    public void enterCity(String cityName) {
        waitForElement(page.locator(city));
        fillElement(city, cityName);
    }

    public void enterPostalCode(String code) {
        waitForElement(page.locator(postalCode));
        fillElement(postalCode, code);
    }

    public void clickContinue() {
        waitForElement(page.locator(continueButton));
        clickElement(continueButton);
    }

    public void waitForShipmentOption() {
        waitForElement(page.locator(shipmentOption));
    }

    public void fillShippingForm(String country, String fname, String lname, String address, String cityName, String code) {
        page.waitForLoadState();
        selectCountry(country);
        enterFirstName(fname);
        enterLastName(lname);
        enterAddress(address);
        enterCity(cityName);
        enterPostalCode(code);
        clickContinue();
        waitForShipmentOption();
        clickContinue();
    }
}