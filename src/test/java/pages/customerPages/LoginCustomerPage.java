package pages.customerPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.common.BasePage;
import pages.common.StartPage;
import utils.ScreenshotComparer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

public class LoginCustomerPage extends BasePage {

    private static final String nameDropDown = "//*[@id=\"userSelect\"]";
    private static final String loginButton = "/html/body/div/div/div[2]/div/form/button";

    private void openDropDown() {
        $(By.xpath(nameDropDown)).click();
    }

    private void chooseName(String name) {
        $(By.id("userSelect")).selectOptionContainingText(name);
    }

    @Step
    private void checkNamePresentInDropDown(String name) {
        openDropDown();
        $(By.id("userSelect")).selectOptionContainingText(name);
    }

    @Step
    public void loginAndGoAccountCustomerPage(String name) {
        openDropDown();
        chooseName(name);
        $(By.xpath(loginButton)).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step
    public LoginCustomerPage checkLoginCustomerPageScreenshots() {
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
