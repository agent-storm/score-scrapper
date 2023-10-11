
import java.awt.Robot;
// import java.lang.Runtime;
import java.nio.file.Path;
import java.time.Duration;
import java.nio.file.Files;
import java.util.ArrayList;
import org.openqa.selenium.By;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;

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
        driv.manage().window().maximize();

        driv.get("https://www.codechef.com/START99");

        Thread.sleep(1000);
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(30));
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
        // TODO Go through all pages in ranks page and add them to Ranks.txt file

        //Testing code
        WebElement pagesList = driv.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/table/tfoot/tr/td/div[1]/nav/ul"));
        String temp[] = pagesList.getText().split("\n");
        int max = 0;
        for (int i = 0; i < temp.length; i++) {
            int t = Integer.parseInt(temp[i]);
            if(t>max)
                max = t;
        }
        System.out.println(max);
        BufferedWriter contentsFile = new BufferedWriter(new FileWriter("score-scrapper\\output-files\\Ranks.txt",true));
        WebElement nextPagebtn = driv.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/table/tfoot/tr/td/div[1]/nav/ul/li[5]/button"));
        while(max-- > 1){
            WebElement rankList = driv.findElement(By.xpath("//*[@id='root']/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/div[2]/table/tbody"));
            // Files.writeString(contentsFile, rankList.getAttribute("innerHTML"));
            contentsFile.write(rankList.getAttribute("innerHTML"));
            Thread.sleep(1000);
            nextPagebtn.click();
            Thread.sleep(5000);
            System.out.println(driv.getCurrentUrl());
            // TODO Driver.get.CurrentURL.
        }
        //Testing code end
        contentsFile.close();
    }
}
