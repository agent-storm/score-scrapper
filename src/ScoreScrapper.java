import java.awt.Robot;
import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import java.awt.event.KeyEvent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ScoreScrapper {
    public static void main(String[] args) throws Exception {
        // TODOPerform the following action for all divisions of the contest.
        System.setProperty("webdriver.chrome.driver", "D:\\Projects\\ScoreScrapper\\chromedriver-win64\\chromedriver.exe");
        WebDriver driv = new ChromeDriver();
        Actions act = new Actions(driv);
        Robot bot = new Robot();

        driv.manage().window().maximize();

        driv.get("https://www.codechef.com/START99");

        Thread.sleep(1000);
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(10));
        WebElement divsionSelector = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ember350\"]")));
        Thread.sleep(2000);
        divsionSelector.click();
        Thread.sleep(1000);
        WebElement contestRanksBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='contest-ranks']/a")));
        contestRanksBtn.click();
        Thread.sleep(1000);
        ArrayList<String> wid = new ArrayList<String>(driv.getWindowHandles());
        System.out.println(wid);
        driv.switchTo().window(wid.get(1));
        Thread.sleep(1000);
        WebElement filterByBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        filterByBtn.click();
        bot.keyPress(KeyEvent.VK_DOWN);
        Thread.sleep(1000);
        bot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
        WebElement filterInputTextArea = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[1]/div/div/div[3]/div/div/div/input")));
        filterInputTextArea.sendKeys("CMR Institute of Technology, Hyderabad");
        Thread.sleep(1000);
        act.moveToElement(filterInputTextArea,5,40).build().perform();
        Thread.sleep(1000);
        // act.contextClick().build().perform();
        act.click().build().perform();
        Thread.sleep(1000);
        WebElement filterApplyBtn = driv.findElement(By.xpath("//*[@id='root']/div/div[3]/div/div/div[2]/div[1]/div/div/div[4]/button"));
        filterApplyBtn.click();
    }


}
