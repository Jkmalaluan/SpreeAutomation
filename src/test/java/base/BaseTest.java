package base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.microsoft.playwright.*;
import com.microsoft.playwright.BrowserContext;
import java.nio.file.Paths;
import java.util.Arrays;

public class BaseTest {
    
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    private static final String TEST_RESULTS_DIR = "target/test-results";

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(true)
            .setTimeout(30000) // 30 second timeout for browser launch
            .setArgs(Arrays.asList(
                "--no-sandbox",
                "--disable-dev-shm-usage", // Prevents issues with limited resource environments
                "--disable-gpu"
            )));
        
        // Create context with tracing options and viewport size
        context = browser.newContext(new Browser.NewContextOptions()
            .setRecordVideoDir(Paths.get(TEST_RESULTS_DIR, "videos"))
            .setRecordHarPath(Paths.get(TEST_RESULTS_DIR, "har", "trace.har"))
            .setViewportSize(1920, 1080)
     );
        
        // Start tracing
        context.tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true)
        );
            
        page = context.newPage();
        
        // Set default timeouts
        page.setDefaultNavigationTimeout(30000);
        page.setDefaultTimeout(10000);
    }

    @AfterClass
    public void tearDown() {
        if (context != null) {
            // Stop tracing and export
            context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get(TEST_RESULTS_DIR, "trace.zip")));
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
