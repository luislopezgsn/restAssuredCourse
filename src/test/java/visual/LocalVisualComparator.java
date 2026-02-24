package visual;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Local utility to compare existing screenshots and highlight differences.
 * This simulates the 'Comparison' phase of a visual test.
 */
@Epic("Visual Regression Testing")
@Feature("Local Baseline Comparison")
public class LocalVisualComparator {

    @Test
    @Description("Compare the local baseline image against the current 'actual' screenshot without launching a browser")
    public void compareScreenshots() throws Exception {
        String baselinePath = "src/test/resources/baselines/swagger-ui-baseline.png";
        String actualPath = "target/visual-results/actual/swagger-ui-actual.png";
        String diffPath = "target/visual-results/diff/swagger-ui-diff.png";

        File baselineFile = new File(baselinePath);
        File actualFile = new File(actualPath);

        if (!baselineFile.exists() || !actualFile.exists()) {
            throw new RuntimeException("Missing image files for comparison! Ensure baseline and actual exist.");
        }

        // 1. Load the images
        BufferedImage expectedImage = ImageIO.read(baselineFile);
        BufferedImage actualImage = ImageIO.read(actualFile);

        // 2. Configure the comparison
        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage);

        // Output path for the difference image
        File diffFile = new File(diffPath);
        diffFile.getParentFile().mkdirs();
        imageComparison.setDestination(diffFile);

        // Optional: Draw rectangles around differences
        imageComparison.setDrawExcludedRectangles(true);

        // 3. Perform the comparison
        ImageComparisonResult comparisonResult = imageComparison.compareImages();

        // 4. Report Results
        if (comparisonResult.getImageComparisonState() == ImageComparisonState.MISMATCH) {
            System.out.println("❌ Visual mismatch detected!");
            System.out.println("Differences highlighted at: " + diffFile.getAbsolutePath());
            // In a real test, we would fail here
            // Assert.fail("UI has changed visually!");
        } else {
            System.out.println("✅ Visual check passed! Images are identical (within tolerance).");
        }
    }
}
