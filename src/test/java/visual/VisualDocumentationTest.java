package visual;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.microsoft.playwright.*;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

/**
 * Visual Test class to ensure the Swagger UI documentation remains consistent.
 */
@Epic("Visual Regression Testing")
@Feature("Documentation UI")
public class VisualDocumentationTest {

    static Playwright playwright;
    static Browser browser;
    static BrowserContext context;

    @BeforeClass
    public static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        // Set a fixed viewport to ensure screenshots are consistent
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1280, 720));
    }

    @AfterClass
    public static void closeBrowser() {
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    @Description("Compare current Swagger UI against the baseline using a live browser")
    public void testSwaggerUIVisual() throws Exception {
        Page page = context.newPage();
        page.navigate("https://videogamedb.uk/");

        // Wait for the Swagger UI container to be visible
        page.waitForSelector(".swagger-ui");

        // Wait a bit more for animations/dynamic content to settle
        page.waitForTimeout(2000);

        String baselinePath = "src/test/resources/baselines/swagger-ui-baseline.png";
        String actualPath = "target/visual-results/actual/swagger-ui-actual.png";
        String diffPath = "target/visual-results/diff/swagger-ui-diff.png";

        // Create directories if they don't exist
        new File("target/visual-results/actual/").mkdirs();
        new File("target/visual-results/diff/").mkdirs();
        new File("src/test/resources/baselines/").mkdirs();

        // Capture current screenshot
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(actualPath)).setFullPage(true));

        File baselineFile = new File(baselinePath);

        if (!baselineFile.exists()) {
            // FIRST RUN: Save actual as baseline
            File actualFile = new File(actualPath);
            actualFile.renameTo(baselineFile);
            System.out.println("✅ Baseline created at: " + baselinePath + ". Run the test again to verify.");
        } else {
            // SUBSEQUENT RUNS: Compare actual against baseline
            BufferedImage expectedImage = ImageIO.read(baselineFile);
            BufferedImage actualImage = ImageIO.read(new File(actualPath));

            ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage);
            imageComparison.setDestination(new File(diffPath));

            // Set some tolerance for minor anti-aliasing differences
            imageComparison.setAllowingPercentOfDifferentPixels(0.1);

            ImageComparisonResult comparisonResult = imageComparison.compareImages();

            if (comparisonResult.getImageComparisonState() == ImageComparisonState.MISMATCH) {
                Assert.fail("❌ Visual mismatch detected! \nBaseline: " + baselinePath +
                        "\nActual: " + actualPath +
                        "\nDifference highlighted at: " + diffPath);
            } else {
                System.out.println("✅ Visual check passed! UI is consistent with baseline.");
            }
        }
    }
}
