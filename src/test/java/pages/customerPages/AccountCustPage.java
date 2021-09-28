package pages.customerPages;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.common.BasePage;
import ru.yandex.qatools.ashot.coordinates.Coords;
import utils.ScreenshotComparer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

public class AccountCustPage extends BasePage {

    private static final String transactionsButton = "/html/body/div/div/div[2]/div/div[3]/button[1]";
    private static final String depositButton = "/html/body/div/div/div[2]/div/div[3]/button[2]";
    private static final String withdrawButton = "/html/body/div/div/div[2]/div/div[3]/button[3]";
    private static final String accountSelectDropDown = "//*[@id=\"accountSelect\"]";
    private static final String welcomeTitle = "/html/body/div/div/div[2]/div/div[1]/strong";
    private static final String accountData = "/html/body/div/div/div[2]/div/div[2]";
    private static final String amountToBeLabel = "/html/body/div/div/div[2]/div/div[4]/div/form/div/label";
    private static final String inputField = "/html/body/div/div/div[2]/div/div[4]/div/form/div/input";
    private static final String submitOperationButton = "/html/body/div/div/div[2]/div/div[4]/div/form/button";
    private static final String operationStatusMessage = "/html/body/div/div/div[2]/div/div[4]/div/span";
    private static final String accountLineData = "//div[@class='borderM box padT20 ng-scope']//*[contains(text(),'Account Number')]";
    private static final String welcomeLine = "//div[@class='borderM box padT20 ng-scope']//div[1]";
    private static final String balance = "//div[contains(@class,\"center\")]/strong[2]";
    private String balanceValue;


    @Step
    public AccountCustPage goTransactionsPage() {
        $(By.xpath(transactionsButton)).click();
        return this;
    }

    @Step
    public AccountCustPage performDeposit(String amount) {
        $(By.xpath(depositButton)).click();
        $(By.xpath(inputField)).setValue(amount);
        $(By.xpath(submitOperationButton)).click();
        checkActionCorrect();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        return this;
    }

    @Step
    public AccountCustPage performWithdraw(String amount) {
        $(By.xpath(withdrawButton)).click();
        $(By.xpath(inputField)).setValue(amount);
        $(By.xpath(submitOperationButton)).click();
        checkActionCorrect();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
        return this;
    }

    @Step
    public AccountCustPage rememberCustomerBalance() {
        balanceValue = $(By.xpath(balance)).getText();
        return this;
    }

    private void checkActionCorrect() {
        assertTrue($(By.xpath(operationStatusMessage)).getText().toLowerCase().contains("successful"), "Operation failed");
    }

    private void checkActionFailed() {
        assertTrue($(By.xpath(operationStatusMessage)).getText().contains("failed"), "Operation failed");
    }

    @Step
    public AccountCustPage checkAccountCustPageDisplayedByScreenshots() {
        Set<Coords> coords = new HashSet();
        Coords area = new Coords(430, 156, 1230,87);
        coords.add(area);

        String pageName = this.getClass().getSimpleName();
        try {
            ScreenshotComparer screenshotComparer = new ScreenshotComparer();
            File expectedFile =
                    new File(System.getProperty("user.dir") + "\\resources\\expectedScreenshots\\" + pageName + ".png");
            screenshotComparer.compareScreenshotsWithIgnoredAreas(coords, expectedFile, pageName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
        return this;
    }

}
