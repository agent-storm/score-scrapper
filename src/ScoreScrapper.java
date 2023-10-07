import java.time.Duration;
import java.util.ArrayList;
// import java.util.ArrayList;
// import java.util.concurrent.TimeUnit;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
// import org.openqa.selenium.JavascriptExecutor;
// import org.openqa.selenium.Keys;
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
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(10));
        // WebElement divsionSelector = driv.findElement(By.xpath("//*[@id=\"ember350\"]"));
        WebElement divsionSelector = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ember350\"]")));
        Thread.sleep(2000);
        divsionSelector.click();
        Thread.sleep(1000);
        // WebElement contestRanksBtn = driv.findElement(By.xpath("//*[@id='contest-ranks']/a"));
        WebElement contestRanksBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='contest-ranks']/a")));
        contestRanksBtn.click();
        Thread.sleep(1000);
        ArrayList<String> wid = new ArrayList<String>(driv.getWindowHandles());
        System.out.println(wid);
        driv.switchTo().window(wid.get(1));
        Thread.sleep(1000);
        // WebElement filterByBtn = driv.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]/div/input"));
        WebElement filterByBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        filterByBtn.click();
        Robot bot = new Robot();
        bot.keyPress(KeyEvent.VK_DOWN);
        Thread.sleep(1000);
        bot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
        WebElement filterInputTextArea = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[1]/div/div/div[3]/div/div/div/input")));
        filterInputTextArea.sendKeys("CMR Institute of Technology, Hyderabad");
        // WebElement ContestRanksBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/rankings/START99D']")));
        // ((JavascriptExecutor)driv).executeScript("arguments[0].click();", ContestRanksBtn);
        // Thread.sleep(5000);
        // WebElement filterType = driv.findElement(By.cssSelector("div[aria-haspopup='listbox']"));
        // filterType.click();

    }


}
