
import java.awt.Robot;
import java.io.FileWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.io.BufferedWriter;
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
        System.setProperty("webdriver.chrome.driver", "score-scrapper\\assets\\chromedriver.exe");
        WebDriver driv = new ChromeDriver();
        Actions act = new Actions(driv);
        Robot bot = new Robot();
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(30));
        driv.manage().window().maximize();
        // May be use multithreading to process all 4 divisions at the same time.
        driv.get("https://www.codechef.com/START99");

        Thread.sleep(1000);
        WebElement divsionSelector = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"ember350\"]")));
        Thread.sleep(2000);
        divsionSelector.click();
        Thread.sleep(1000);
        WebElement contestRanksBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='contest-ranks']/a")));
        contestRanksBtn.click();
        Thread.sleep(1000);
        ArrayList<String> wid = new ArrayList<String>(driv.getWindowHandles());
        driv.switchTo().window(wid.get(1));
        Thread.sleep(10000);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        WebElement filterByBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        filterByBtn.click();
        bot.keyPress(KeyEvent.VK_DOWN);
        Thread.sleep(1000);
        bot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
        WebElement filterInputTextArea = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[1]/div/div/div[3]/div/div/div/input")));
        filterInputTextArea.sendKeys("CMR Institute of Technology, Hyderabad");
        Thread.sleep(3000);
        act.moveToElement(filterInputTextArea,5,40).build().perform();
        Thread.sleep(1000);
        act.click().build().perform();
        Thread.sleep(1000);
        WebElement filterApplyBtn = driv.findElement(By.xpath("//*[@id='root']/div/div[3]/div/div/div[2]/div[1]/div/div/div[4]/button"));
        filterApplyBtn.click();
        Thread.sleep(5000);

        // Data reading part.
        WebElement pagesList = driv.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/table/tfoot/tr/td/div[1]/nav/ul"));
        String temp[] = pagesList.getText().split("\n");
        int max = 0;
        for (int i = 0; i < temp.length; i++) {
            int t = Integer.parseInt(temp[i]);
            if(t>max)
                max = t;
        }
        BufferedWriter contentsFile = new BufferedWriter(new FileWriter("score-scrapper\\output-files\\raw_html_scores.txt",true));
        Thread.sleep(1000);
        int pageCount = 1;
        while(max-- > 1){
            System.out.println("=> Reading page no:"+pageCount);
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/div[2]/table/tbody")));
            WebElement ranksList = driv.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/div[2]/table/tbody"));
            contentsFile.write(ranksList.getAttribute("innerHTML"));
            System.out.println("=> Write Complete.");
            Thread.sleep(1000);
            WebElement nextPagebtn = driv.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/table/tfoot/tr/td/div[1]/nav/ul/li[5]/button"));
            nextPagebtn.click();
            Thread.sleep(5000);
            pageCount++;
        }
        System.out.println("=> Reading page no:"+(pageCount+1));
        WebElement ranksList = driv.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/div[2]/table/tbody"));
        contentsFile.write(ranksList.getAttribute("innerHTML"));
        contentsFile.close();
        System.out.println("------------ Finished loading raw HTML to raw_html_scores.txt ------------");
    }
}
