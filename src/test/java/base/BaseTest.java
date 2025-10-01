package base;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
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
        
        // Enhanced browser launch options for CI environment
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(true)
            .setTimeout(45000)
            .setArgs(Arrays.asList(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-web-security",
                "--disable-features=IsolateOrigins,site-per-process",
                "--disable-setuid-sandbox",
                "--window-size=1920,1080",
                "--force-gpu-mem-available-mb=1024"
            )));
        
        // Create context with more resilient settings
        context = browser.newContext(new Browser.NewContextOptions()
            .setRecordVideoDir(Paths.get(TEST_RESULTS_DIR, "videos"))
            .setRecordHarPath(Paths.get(TEST_RESULTS_DIR, "har", "trace.har"))
            .setViewportSize(1920, 1080)
            .setBypassCSP(true)
            .setIgnoreHTTPSErrors(true)
            .setServiceWorkers(ServiceWorkerPolicy.ALLOW)  // Allow service workers
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36")
            .setExtraHTTPHeaders(new java.util.HashMap<String, String>() {{
                put("Accept-Language", "en-US,en;q=0.9");
                put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                put("Connection", "keep-alive");
                put("Upgrade-Insecure-Requests", "1");
            }})
        );
        
        // Start tracing with more details
        context.tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true)
        );
            
        page = context.newPage();
        
        // Configure page settings
        page.setDefaultNavigationTimeout(45000);
        page.setDefaultTimeout(30000);
        
        // Wait for all resource types
        page.route("**/*", route -> {
            route.resume(); // Fixed method name from continue_() to resume()
        });
    }

    @AfterClass
    public void tearDown() {
        if (context != null) {
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
