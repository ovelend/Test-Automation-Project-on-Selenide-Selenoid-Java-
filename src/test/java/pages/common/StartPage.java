package pages.common;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.ScreenshotComparer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

public class StartPage extends BasePage {

    private static final String customerLoginButton = ".//div/button[@class='btn btn-primary btn-lg']";
    private static final String bankManagerLogin = "//div[2]/button";

    @Step
    public StartPage goCustomerLoginPage() {
        $(By.xpath(customerLoginButton)).click();
        return this;
    }

    @Step
    public StartPage goBankManagerLoginPage() {
        $(By.xpath(bankManagerLogin)).click();
        return this;
    }

    @Step
    public StartPage checkStartPageDisplayedByScreenshots() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            String pageName = this.getClass().getSimpleName();
            ScreenshotComparer screenshotComparer = new ScreenshotComparer();
            File expectedFile =
                    new File(System.getProperty("user.dir") + "\\resources\\expectedScreenshots\\" + pageName + ".png");
            screenshotComparer.compareScreenshots(expectedFile, pageName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        return this;
    }
}
