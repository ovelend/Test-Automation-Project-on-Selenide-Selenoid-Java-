package utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.comparison.PointsMarkupPolicy;
import ru.yandex.qatools.ashot.coordinates.Coords;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;;
import java.util.Set;
import static org.testng.Assert.fail;

public class ScreenshotComparer {

    Robot robot;

    public ScreenshotComparer() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private Rectangle getScreenshotAreaSize() {
        Rectangle screenshotArea = new Rectangle(380, 100, 1160, 700);
        return screenshotArea;
    }

    @Step
    public void compareScreenshots(File expectedFile, String pageName) throws IOException, AWTException {
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        BufferedImage actualScreenshot = robot.createScreenCapture(getScreenshotAreaSize());
        ImageIO.write(actualScreenshot, "png",
                new File(getActualFilePath(pageName)));
        File actualFile = new File(getActualFilePath(pageName));
        Screenshot expected = new Screenshot(ImageIO.read(expectedFile));
        Screenshot actual = new Screenshot(ImageIO.read(actualFile));
        executeComparison(expected, actual, pageName);
    }

    @Step
    public void compareScreenshotsWithIgnoredAreas(Set<Coords> ignoredAreas, File expectedFile, String pageName) throws IOException, AWTException {
        float b = 1.15f;
        Screenshot actual = new AShot()
                .shootingStrategy(ShootingStrategies.viewportRetina(0, 0, 0, b))
                .coordsProvider(new WebDriverCoordsProvider())
                .ignoredAreas(ignoredAreas)
                .takeScreenshot(WebDriverRunner.getWebDriver());
        ImageIO.write(actual.getImage(), "png", new File(getActualFilePath(pageName)));
        Screenshot expected = new Screenshot(ImageIO.read(expectedFile));
        expected.setIgnoredAreas(actual.getIgnoredAreas());
        executeComparison(expected, actual, pageName);

    }

    private void executeComparison(Screenshot expected, Screenshot actual, String pageName) {
        PointsMarkupPolicy diffMarkupPolicy = new PointsMarkupPolicy();
        diffMarkupPolicy.setDiffSizeTrigger(20);
        ImageDiffer differ = new ImageDiffer();
        ImageDiff diff = differ.makeDiff(expected, actual);
        try {
            if (diff.getDiffSize() != 0) {
                BufferedImage diffImage = diff.getDiffImage();
                File diffFile = new File(System.getProperty(
                        "user.dir") + "\\resources\\diffBetweenImages\\diff" + pageName + "_" + getCurrentTime() + ".png");
                ImageIO.write(diff.getMarkedImage(), "png", diffFile);
                System.out.println("\n diffImage = " + diffImage.getColorModel());
                getBytes(System.getProperty(
                        "user.dir") + "\\resources\\diffBetweenImages\\diff" + pageName + "_" + getCurrentTime() + ".png");
                fail("Screenshots are different");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //deleteAllFilesFolder(System.getProperty("user.dir") + "\\resources\\diffBetweenImages");
            deleteAllFilesFolder(System.getProperty("user.dir") + "\\resources\\screenshotReports");
        }
    }

    @Attachment
    private static byte[] getBytes(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get(resourceName));
    }

    private static void deleteAllFilesFolder(String path) {
        for (File myFile : new File(path).listFiles())
            if (myFile.isFile()) myFile.delete();
    }

    private static String getActualFilePath(String pageName) {
        return System.getProperty("user.dir") + "\\resources\\screenshotReports\\" + pageName + ".png";
    }

    private static String getCurrentTime() {
        DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime localTime = LocalDateTime.now();
        String time = dataFormat.format(localTime).replaceAll(":","_").replaceAll("/","_");
        return time;
    }

}

