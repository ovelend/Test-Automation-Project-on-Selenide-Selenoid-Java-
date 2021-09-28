package tests.customerTests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.AllureId;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tests.TestBase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.open;


public class LoginTest extends TestBase {


    @BeforeSuite
    private void checkMainTile() {
        System.setProperty("wdm.proxy", START_PAGE_URL);
        Configuration.startMaximized = true;
        //Configuration.headless = true;
    }

    @Test(groups = {"smoke"})
    @Description("[UI]: A customer is able to go to his personal account")
    public void checkUserCanLoginAndLogout() {
        open(START_PAGE_URL);
        startPage
                .checkStartPageDisplayedByScreenshots()
                .goCustomerLoginPage();
        loginCustomerPage
                .checkLoginCustomerPageScreenshots()
                .loginAndGoAccountCustomerPage("Ron Weasly");
        accountCustPage
                .checkAccountCustPageDisplayedByScreenshots()
                .logout();
        loginCustomerPage.checkLoginCustomerPageScreenshots();
    }

}
