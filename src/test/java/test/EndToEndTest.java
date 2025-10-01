package test;

import org.testng.annotations.Test;
import base.BaseTest;
import pages.*;
import org.testng.Assert;
import test.config.TestData;
import test.config.OrderTestData;

public class EndToEndTest extends BaseTest {
    private RegistrationPage registrationPage;
    private ProductPage productPage;
    private ProductDetailsPage detailsPage;
    private CheckoutPage checkoutPage;
    private PaymentPage paymentPage;
    private String randomEmail;
    
    // Test data fields to store current test data
    private String productName;
    private String size;
    private String country;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String postalCode;
    
    @Test
    public void navigateToHomePage() {
        registrationPage = new RegistrationPage(page);
        productPage = new ProductPage(page);
        detailsPage = new ProductDetailsPage(page);
        checkoutPage = new CheckoutPage(page);
        paymentPage = new PaymentPage(page);
        
        page.navigate(TestData.BASE_URL);
        page.waitForLoadState();
        Assert.assertTrue(registrationPage.isWelcomeMessageDisplayed(), "Welcome message is not visible");
        Assert.assertEquals(page.url(), TestData.BASE_URL, "Invalid URL.");
    }
    
    @Test(dependsOnMethods = "navigateToHomePage")
    public void signUp() {
        registrationPage.clickProfileButton();
        registrationPage.clickSignUpLink();
        
        randomEmail = "user" + System.currentTimeMillis() + "@test.com";
        registrationPage.addEmail(randomEmail);
        registrationPage.addPassword(TestData.UserData.PASSWORD);
        registrationPage.addConfirmPassword(TestData.UserData.PASSWORD);
        registrationPage.clickSignUpButton();
        Assert.assertTrue(registrationPage.isSuccessMessageDisplayed(), "Success message is not visible");
        registrationPage.clickProfileLoggedInButton();
        registrationPage.clickLogoutButton();
    }
    
    @Test(dependsOnMethods = "signUp")
    public void login() {
        registrationPage.clickProfileButton();
        registrationPage.addEmail(randomEmail);
        registrationPage.addPassword(TestData.UserData.PASSWORD);
        registrationPage.clickLogin();
        Assert.assertTrue(registrationPage.isLoginSuccess(), "Success message is not visible");
    }
    
    @Test(dependsOnMethods = "login", dataProvider = "orderData", dataProviderClass = OrderTestData.class)
    public void searchAndSelectProduct(String productName, String size, String country, 
                                     String firstName, String lastName, String address, 
                                     String city, String postalCode) {
        // Store test data for use in dependent methods
        this.productName = productName;
        this.size = size;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        
        productPage.clickSearchButton();
        productPage.enterProductName(productName);
        productPage.clickProduct(productName);
        detailsPage.selectSizeOption(size);
        detailsPage.clickAddToCart();

        OrderDetails orderDetails = detailsPage.getOrderDetails();
        
        Assert.assertEquals(orderDetails.getOrderName(), productName, "Product name doesn't match");
        Assert.assertEquals(orderDetails.getOrderQuantity(), "1", "Quantity should be 1 by default");
        Assert.assertNotNull(orderDetails.getOrderTotal(), "Order total should not be null");
    }
    
    @Test(dependsOnMethods = "searchAndSelectProduct")
    public void checkout() {
        detailsPage.clickCheckOUt();
        checkoutPage.fillShippingForm(
            country,
            firstName,
            lastName,
            address,
            city,
            postalCode
        );
    }
    
    @Test(dependsOnMethods = "checkout")
    public void payment() {
        paymentPage.enterCardDetails(
            TestData.PaymentData.CARD_NUMBER,
            TestData.PaymentData.CARD_EXPIRY,
            TestData.PaymentData.CARD_CVC
        );
        
        paymentPage.clickPayNow();

        Assert.assertTrue(paymentPage.isSuccessMessageDisplayed(), "Order confirmation message is not visible");
        Assert.assertNotNull(paymentPage.getOrderNumber(), "Order number should not be null");
    }
}