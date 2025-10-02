package pages;

import com.microsoft.playwright.Page;

public class RegistrationPage extends BasePage {
    
    // Element locators
    private final String signUpLink = "//a[contains(text(), 'Sign Up')]";
    private final String emailField = "input[id='user_email']";
    private final String passwordField = "input[id='user_password']";
    private final String confirmPasswordField = "input[id='user_password_confirmation']";
    private final String signUpButton = "input[value='Sign Up']";
    private final String successMessage = "//p[contains(text(),'Welcome! You have signed up successfully.')]";
    private final String loginSuccessMessage = "//p[contains(text(),'Signed in successfully.')]";
    private final String logoutButton = "//button[text()='Log out']";
    private final String profileButton = "//div[@class='flex items-center gap-4 flex-1 justify-end']/div[2]/button";
    private final String profileLoggedInButton = "//div[@class='flex items-center gap-4 flex-1 justify-end']/div[2]/a";
    //private final String profileButton = "//button[contains(@class, 'profile-button')]";      
    //private final String profileLoggedInButton = "//a[contains(@class, 'profile-link')]";
    
    private final String loginButton = "input[id='login-button']";
    private final String welcomeMessage = "//div[contains(text(),'Welcome to this Spree Commerce demo website')]";

    public RegistrationPage(Page page) {
        super(page);
    }

    public void clickSignUpLink() {
        clickElement(signUpLink);
        waitForElement(page.locator(confirmPasswordField));
    }

    public void clickProfileButton() {
        waitForElement(page.locator(profileButton));
        clickElementWithValidation(profileButton,
        "//div[@id='account-pane' and contains(@class, 'opacity-100') and not(contains(@class, 'hidden'))]");
    }

    public void clickProfileLoggedInButton() {
        clickElement(profileLoggedInButton);
    }
    public void addEmail(String email) {
        fillElement(emailField, email);
    }

    public void addPassword(String password) {
        fillElement(passwordField, password);
    }

    public void addConfirmPassword(String password) {
        fillElement(confirmPasswordField, password);
    }

    public void clickSignUpButton() {
        clickElement(signUpButton);
    }

    public void clickLogin() {
        clickElement(loginButton);
    }

    public void clickLogoutButton() {
        clickElement(logoutButton);
    }

    public boolean isSuccessMessageDisplayed() {
        waitForElement(page.locator(successMessage));
        return page.isVisible(successMessage);
    }

    public boolean isLoginSuccess() {
        try {
            waitForElement(page.locator(loginSuccessMessage));
            return page.locator(loginSuccessMessage).isVisible() || 
                   page.locator("//div[contains(@class, 'logged-in')]").isVisible() ||
                   page.locator(profileLoggedInButton).isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWelcomeMessageDisplayed() {
        return page.isVisible(welcomeMessage);
    }
}
