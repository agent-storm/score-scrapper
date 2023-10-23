
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
        catch(Exception e){}
    }

    public void Reader() throws Exception{

        System.setProperty("webdriver.chrome.driver", "assets\\chromedriver.exe");
        WebDriver driv = new ChromeDriver();
        Actions act = new Actions(driv);
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(10));
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
        if(!filterApplyBtn.isEnabled()){
            System.out.println("The following div does not have data:"+this.divName);
            this.interrupt();
        }
        filterApplyBtn.click();
        Thread.sleep(5000);
        // Data reading part.
        WebElement nextPagebtn = driv.findElement(By.className("_next-pagination__item_ehs9q_89"));
        String filePath = "output-files\\raw_html_"+divName+"_scores.txt";
        BufferedWriter contentsFile = new BufferedWriter(new FileWriter(filePath,true));
        if (nextPagebtn.isEnabled()){
            WebElement pagesList = driv.findElement(By.className("MuiPagination-ul"));
            String temp[] = pagesList.getText().split("\n");
            int max = 0;
            for (int i = 0; i < temp.length; i++) {
                try{ 
                    int t = Integer.parseInt(temp[i]); 
                    if(t>max)
                    max = t;
                }
                catch(Exception e){}
                
            }
            Thread.sleep(3000);
            if(max>1){
                while(max > 1){
                    WebElement ranksList = driv.findElement(By.className("MuiTableBody-root"));
                    contentsFile.write(ranksList.getAttribute("innerHTML"));
                    Thread.sleep(1000);
                    driverWait.until(ExpectedConditions.elementToBeClickable(By.className("_next-pagination__item_ehs9q_89")));
                    WebElement nextPagebtnRef = driv.findElement(By.className("_next-pagination__item_ehs9q_89"));
                    nextPagebtnRef.click();
                    Thread.sleep(5000);
                    max-=1;
                }
            }
        }
        WebElement ranksList = driv.findElement(By.className("MuiTableBody-root"));
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
        // Thread.sleep(1000);
        // t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
        t3.start();
        Thread.sleep(1000);
        t4.start();
    }
}
