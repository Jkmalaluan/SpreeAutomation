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
        
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(true)
            .setTimeout(45000)
            .setArgs(Arrays.asList(
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-web-security",
                "--disable-features=IsolateOrigins",
                "--disable-setuid-sandbox",
                "--start-maximized",
                "--force-gpu-mem-available-mb=1024"
            )));
        
        context = browser.newContext(new Browser.NewContextOptions()
            .setRecordVideoDir(Paths.get(TEST_RESULTS_DIR, "videos"))
            .setRecordHarPath(Paths.get(TEST_RESULTS_DIR, "har", "trace.har"))
              // Set a large standard viewport size
            .setBypassCSP(true)
            .setIgnoreHTTPSErrors(true)
            .setServiceWorkers(ServiceWorkerPolicy.ALLOW)
            .setHasTouch(true)
            .setJavaScriptEnabled(true)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36")
            .setExtraHTTPHeaders(new java.util.HashMap<String, String>() {{
                put("Accept-Language", "en-US,en;q=0.9");
                put("Accept", "*/*");
                put("Connection", "keep-alive");
                put("Cache-Control", "no-cache");
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
        page.route("**/*", route -> route.resume());
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
