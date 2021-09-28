package tests.pdfTests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class TestPDF {


    // needs to be finished
    @Test
    public void checkThePageScrolledTillTheEnd() {

        System.setProperty("webdriver.chrome.driver", "C:\\Windows\\System32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://lux-coffee.ru/doc/bosch-verocappuccino-tes556m1ru.pdf");
        driver.manage().window().maximize();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        Long value = (Long) executor.executeScript("return window.pageYOffset;");
        driver.close();
    }
}
