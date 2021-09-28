package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.common.BasePage;
import pages.common.StartPage;
import pages.customerPages.AccountCustPage;
import pages.customerPages.LoginCustomerPage;
import pages.customerPages.TransactionPage;
import pages.managerPages.ManagerPage;

public class TestBase {

    public static BasePage basePage;
    public static StartPage startPage;
    public static AccountCustPage accountCustPage;
    public static LoginCustomerPage loginCustomerPage;
    public static TransactionPage transactionPage;
    public static ManagerPage managerPage;
    public static final String START_PAGE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";


    @BeforeClass
    public static void setup() {
        setBrowserSettings();
        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        basePage = new BasePage();
        startPage = new StartPage();
        accountCustPage = new AccountCustPage();
        loginCustomerPage = new LoginCustomerPage();
        transactionPage = new TransactionPage();
        managerPage = new ManagerPage();
    }

    @AfterClass
    public static void teardown() {
        Selenide.closeWebDriver();
       /* String stopContainers = "docker stop selenoid selenoid-ui";
        String command = "cmd.exe /c start "+"allure serve allure-results";
        try {
            Runtime.getRuntime().exec(stopContainers);
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static void setBrowserSettings() {
        Configuration.reportsFolder = "test/screenshotReports"; // селенид сохраняет сюда скрины при падении
        Configuration.remote = "http://localhost:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.startMaximized = true;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @Step
    public void goAccountCustPage(String name) {
        startPage.goCustomerLoginPage();
        loginCustomerPage.loginAndGoAccountCustomerPage(name);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        accountCustPage.checkAccountCustPageDisplayedByScreenshots();
    }

}
