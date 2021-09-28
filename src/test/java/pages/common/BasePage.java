package pages.common;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertTrue;

public class BasePage {

    private static final String MAIN_TILE = ".//div//*[@class='box mainhdr']";
    private static final String BANK_TITLE = ".//div//*[@class='mainHeading']";
    private static final String GO_HOME_BUTTON = "/html/body/div/div/div[1]/button[1]";
    private static final String LOGOUT = "/html/body/div/div/div[1]/button[2]";


    @Step
    public void checkMainTile() {
        $(By.xpath(MAIN_TILE)).shouldBe(Condition.visible);
        assertTrue($(By.xpath(BANK_TITLE)).getText().contains("XYZ Bank"), "Title isn't correct");
    }

    @Step
    public void goHome() {
        $(By.xpath(GO_HOME_BUTTON)).click();
    }

    @Step
    public void logout() {
        $(By.xpath(LOGOUT)).click();
    }

    public boolean isElementDisplayed(By findBy) {
        return $(findBy).isDisplayed();
    }

    public void openDropDownAndChoose(By dropdown, String exactOption) {
        $(dropdown).click();
        $(By.xpath(exactOption)).click();
    }

}
