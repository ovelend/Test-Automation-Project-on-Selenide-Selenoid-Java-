package utils;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.atBottom;
import static org.testng.Assert.assertTrue;

public class ScrollPageHelper {

    @Step
    public static void checkPageBottomIsReached() {
        Selenide.executeJavaScript("return window.scrollTo(0, document.body.scrollHeight);");
        assertTrue(atBottom(), "Can't scroll the page till the end" + "\n" + printScrollParams());
    }

    private static String printScrollParams() {
        return "\nwindow.scrollY=" + Selenide.executeJavaScript("return window.pageYOffset;") +
                "\nwindow.innerHeight=" + Selenide.executeJavaScript("return window.innerHeight;") +
                "\ndocument.body.scrollHeight=" + Selenide.executeJavaScript("return document.body.scrollHeight;");
    }
}
