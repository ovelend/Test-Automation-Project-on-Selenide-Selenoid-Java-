package pages.customerPages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import pages.common.BasePage;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertEquals;


public class TransactionPage extends BasePage {

    private static final String dateTimeButton = "//table/thead/tr/td[1]/a";
  //  private static final String firstSortedLineTable = "//*[@id=\"anchor0\"]//td[1]";
    private static final String amountTable =  "//tbody/*[@id=\"anchor0\"]/td[2]";
    private static final String operationTypeTable = "//tbody/*[@id=\"anchor0\"]/td[3]";
    private static final String backButton = "//div[contains(@class,'fixedTopBox')]/button[1]";
    private static final String resetButton = "//div[contains(@class,'fixedTopBox')]/button[2]";
    private static final String startDate = "//*[@id=\"start\"]";
    private static final String endDate = "//*[@id=\"end\"]";

    @Step
    public TransactionPage sortAndCheckTransactionCorrect(String expAmount, String expOperationType) {
        chooseCalendarYear();
        $(By.xpath(dateTimeButton)).click();
        String money = $(By.xpath(amountTable)).getText();
        String operationType = $(By.xpath(operationTypeTable)).getText();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(money,expAmount,"Amount doesn't match");
        softAssert.assertEquals(operationType,expOperationType,"Operation type doesn't match");
        softAssert.assertAll();
        return this;
    }

    private TransactionPage chooseCalendarYear() {
        Selenide.actions()
                .moveToElement($(By.xpath(endDate)), -50, 0)
                .click().sendKeys("1","4","0","7","2","0","2","1");
        return this;
    }

    @Step
    public TransactionPage goBackToCustAccountPage() {
        $(By.xpath(backButton)).click();
        return this;
    }

    @Step
    public TransactionPage performResetTransactions() {
        $(By.xpath(resetButton)).click();
        return this;
    }


}
