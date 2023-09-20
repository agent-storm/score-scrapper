import java.time.Duration;
// import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScoreScrapper {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\ScoreScrapper\\chromedriver-win64\\chromedriver.exe");
        WebDriver driv = new ChromeDriver();
        driv.manage().window().maximize();
        driv.get("https://www.codechef.com/START99");
        Thread.sleep(1000);
        WebElement divsionSelector = driv.findElement(By.id("ember350"));
        Thread.sleep(1000);
        divsionSelector.click();
        Thread.sleep(1000);
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(10));
        WebElement ContestRanksBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/rankings/START99D']")));
        ((JavascriptExecutor)driv).executeScript("arguments[0].click();", ContestRanksBtn);
        Thread.sleep(5000);
        WebElement filterType = driv.findElement(By.cssSelector("div[aria-haspopup='listbox']"));
        filterType.click();

    }


}
