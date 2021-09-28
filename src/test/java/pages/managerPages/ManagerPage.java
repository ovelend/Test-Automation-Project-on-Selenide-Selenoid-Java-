package pages.managerPages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.common.BasePage;
import utils.ScreenshotComparer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ManagerPage extends BasePage {

    // common page elements
    private static final String addCustomerButton = "/html/body/div/div/div[2]/div/div[1]/button[1]";
    private static final String openAccountButton = "/html/body/div/div/div[2]/div/div[1]/button[2]";
    private static final String customersButton = "/html/body/div/div/div[2]/div/div[1]/button[3]";
    // Add Customer form
    private static final String inputFirstName = "/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input";
    private static final String inputLastName = "/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input";
    private static final String inputPostCode = "/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input";
    private static final String submitAddCustomerButton = "//form/button";
    // Open Account form
    private static final String customerDropDown = ".//*[@id ='userSelect']";
    private static final String currencyDropDown = ".//*[@id='currency']";
    private static final String submitProcessButton = "/html/body/div/div/div[2]/div/div[2]/div/div/form/button";
    // Customers form
    private static final String inputSearchCustomer = "/html/body/div/div/div[2]/div/div[2]/div/form/div/div/input";
    private static final String dataTableBody = "//table/tbody/tr";
    private static final String deleteCustomerButton = "//tr/td[5]/button";

    @Step
    public ManagerPage goAddCustomer() {
        $(By.xpath(addCustomerButton)).click();
        return this;
    }

    @Step
    public ManagerPage goOpenAccount() {
        $(By.xpath(openAccountButton)).click();
        return this;
    }

    @Step
    public ManagerPage goCustomerSearch() {
        $(By.xpath(customersButton)).click();
        return this;
    }

    @Step
    public ManagerPage performAddCustomer(String firstName, String lastName, String postCode) {
        goAddCustomer();
        $(By.xpath(inputFirstName)).val(firstName);
        $(By.xpath(inputLastName)).val(lastName);
        $(By.xpath(inputPostCode)).val(postCode);
        $(By.xpath(submitAddCustomerButton)).pressEnter();
        return this;
    }

    @Step
    public ManagerPage performOpenAccount(String customer, String currency) {
        String customerXpath = ".//*[@name='userSelect']//option[contains(text(),'" + customer + "')]";
        String currencyXpath = ".//*[@name='currency']//option[contains(text(),'" + currency + "')]";
        openDropDownAndChoose(By.xpath(customerDropDown), customerXpath);
        openDropDownAndChoose(By.xpath(currencyDropDown), currencyXpath);
        $(By.xpath(submitProcessButton)).pressEnter();
        return this;
    }

    @Step
    public ManagerPage performCustomerSearch(String data) {
        $(By.xpath(inputSearchCustomer)).val(data);
        return this;
    }

    @Step
    public ManagerPage checkCustomerShouldExist(String data) {
        String currencyXpath = dataTableBody + "/*[contains(text(),'" + data + "')]";
        assertTrue(isElementDisplayed(By.xpath(currencyXpath)), "Customer is not found but should exist");
        return this;
    }

    @Step
    public ManagerPage checkCustomerShouldNotExist(String data) {
        String currencyXpath = dataTableBody + "/*[contains(text(),'" + data + "')]";
        assertFalse(isElementDisplayed(By.xpath(currencyXpath)), "Customer is found but shouldn't exist");
        return this;
    }

    @Step
    public ManagerPage performDeleteCustomer(String data) {
        $(By.xpath(customersButton)).click();
        performCustomerSearch(data);
        $(By.xpath(deleteCustomerButton)).click();
        return this;
    }

    @Step
    public ManagerPage checkManagerPageScreenshots() {
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
