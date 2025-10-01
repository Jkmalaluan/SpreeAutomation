package base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.microsoft.playwright.*;

public class BaseTest {
    
    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @AfterClass
    public void tearDown() {
        if(browser != null)
            browser.close();
        if(playwright != null)
            playwright.close();
    }
}
