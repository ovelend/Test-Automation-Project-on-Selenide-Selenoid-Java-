package tests.customerTests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.open;

public class TransactionsTests extends TestBase {

    private String testName = "Harry Potter";
    private String testAmount1 = "130555";
    private String testAmount2 = "9";
    private String creditType = "Credit";
    private String debitType = "Debit";

    @BeforeClass
    private void setUp() {
        System.setProperty("wdm.proxy", START_PAGE_URL);
        Configuration.startMaximized = true;
    }

    @Test(groups = {"smoke"})
    @Description("[UI]: A customer is able to make deposit and see it on TransactionsPage")
    public void checkUserAbleMakeDeposit() {
        open(START_PAGE_URL);
        goAccountCustPage(testName);
        accountCustPage
                .rememberCustomerBalance()
                .performDeposit(testAmount1)
                .goTransactionsPage();
        transactionPage
                .sortAndCheckTransactionCorrect(testAmount1,creditType);
    }

    @Test(groups = {"smoke"})
    @Description("[UI]: A customer is able to withdraw money and see it on TransactionsPage")
    public void checkUserAblePerformWithdraw() {
        open(START_PAGE_URL);
        goAccountCustPage(testName);
        accountCustPage
                .performDeposit(testAmount1)
                .rememberCustomerBalance()
                .performWithdraw(testAmount2)
                .goTransactionsPage();
        transactionPage
                .sortAndCheckTransactionCorrect(testAmount2,debitType);
    }
}
