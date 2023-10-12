
import java.io.FileWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.io.BufferedWriter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class ScoreScrapper extends Thread{
    String divName,divId,pageLink;

    ScoreScrapper(String div,String link){
        this.pageLink = link;
        this.divName = div;
        switch(div){
            case "div-1":
                this.divId = "ember335";break;
            case "div-2":
                this.divId = "ember340";break;
            case "div-3":
                this.divId = "ember345";break;
            case "div-4":
                this.divId = "ember350";break;
        }
    }

    public void run(){
        System.out.println(this.getName()+" Started.");
        try{Reader();}
        catch(Exception e){System.out.println(e);}
    }

    public void Reader() throws Exception{

        System.setProperty("webdriver.chrome.driver", "score-scrapper\\assets\\chromedriver.exe");
        WebDriver driv = new ChromeDriver();
        Actions act = new Actions(driv);
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(30));
        driv.manage().window().maximize();
        driv.get(pageLink);
        String divBtnXpath = "//*[@id='#']"; 
        divBtnXpath = divBtnXpath.replace("#", divId);
        Thread.sleep(1000);
        WebElement divsionSelector = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(divBtnXpath)));
        Thread.sleep(2000);
        divsionSelector.click();
        Thread.sleep(1000);
        WebElement contestRanksBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='contest-ranks']/a")));
        contestRanksBtn.click();
        Thread.sleep(1000);
        ArrayList<String> wid = new ArrayList<String>(driv.getWindowHandles());
        driv.switchTo().window(wid.get(1));
        Thread.sleep(2000);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        WebElement filterByBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        filterByBtn.click();
        Thread.sleep(1000);
        WebElement institutionOption = driv.findElement(By.xpath("/html/body/div[3]/div[3]/ul/li[2]"));
        institutionOption.click();
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
        String filePath = "score-scrapper\\output-files\\raw_html_"+divName+"_scores.txt";
        BufferedWriter contentsFile = new BufferedWriter(new FileWriter(filePath,true));
        Thread.sleep(1000);
        System.out.println(divName+" max:"+max);
        if(max!=1){
            while(max-- > 1){
                driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/div[2]/table/tbody")));
                WebElement ranksList = driv.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/div[2]/table/tbody"));
                contentsFile.write(ranksList.getAttribute("innerHTML"));
                Thread.sleep(1000);
                WebElement nextPagebtn = driv.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/table/tfoot/tr/td/div[1]/nav/ul/li[5]/button"));
                nextPagebtn.click();
                Thread.sleep(5000);
            }
        }
        WebElement ranksList = driv.findElement(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/div[2]/table/tbody"));
        contentsFile.write(ranksList.getAttribute("innerHTML"));
        contentsFile.close();
        System.out.println("------------ Finished loading raw HTML to raw_html_"+divName+"_scores.txt ------------");
        driv.quit();
    }
    public static void main(String[] args) throws Exception{
        String link = "https://www.codechef.com/START99";
        // ScoreScrapper t1 = new ScoreScrapper("div-1",link);
        ScoreScrapper t2 = new ScoreScrapper("div-2",link);
        ScoreScrapper t3 = new ScoreScrapper("div-3",link);
        ScoreScrapper t4 = new ScoreScrapper("div-4",link);
        // t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
