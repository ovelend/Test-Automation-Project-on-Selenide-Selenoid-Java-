package tests.featureDemonstration;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Description;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.open;
import static utils.ScrollPageHelper.checkPageBottomIsReached;

public class DemonstrationTests {

    @Test(groups = {"smoke"})
    @Description("Return true if bottom of the page is reached Useful if you need to scroll down by x pixels unknown number of times.")
    public void scrollPageTillTheBottom() {
        open("https://selenide.org/javadoc/current/com/codeborne/selenide/Selenide.html");
        Configuration.startMaximized = true;
        checkPageBottomIsReached();
    }



}
