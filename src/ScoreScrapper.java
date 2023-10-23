/*
    This is a rather simple looking and naive approach to collecting and processing the raw data obtained from score boards of the 
Code Chef's, START series of contests. @ https://codechef.com/ 

Key Points:
----------------
=> Scrapping of all four divisions is possible. [GOTO: Main Method]
=> Each division can be executed on a seperate Thread.
=> Default filter options are:    [GOTO: line-]
    - By "institution"
    - "CMR Institute of Technology"
=> Data collection is done in java while processing is done in Python.
=> Selenium with java is used for automating some steps to go to the desired webpage and apply some settings
=> Beautifulsoup-Python is used for converting the raw HTML to useful data and putting it in a XLSX file.

Step by Step detailed explaination of the working can be found with in the code in the form of comments.


Contace me 
----------------
@ gujarathisaisrinith@gmail.com
@ https://github.com/agent-storm

Current file repo: https://github.com/agent-storm/score-scrapper
*/ 

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
    // Instance variables to store the Division-name, Division-ID, and the PageLink.
    String divName,divId,pageLink;
    // Constructor
    // Initializes the instance variables according to the arguments.
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
    // Run method for threads.
    // Calles the Reader method.
    public void run(){
        System.out.println(this.getName()+" Started.");
        try{Reader();}
        catch(Exception e){}
    }
    // Core method that does all the work.
    public void Reader() throws Exception{ // I understand its bad to use "throws Exception" but its a small concept program, so its fine.
        // Basic selenium initialisations.
        System.setProperty("webdriver.chrome.driver", "assets\\chromedriver.exe");
        WebDriver driv = new ChromeDriver();
        Actions act = new Actions(driv);
        WebDriverWait driverWait = new WebDriverWait(driv, Duration.ofSeconds(10));
        driv.manage().window().maximize();
        driv.get(pageLink);
        // Create a temporary string that has a place holder '#' for the ID of the Division button.
        String divBtnXpath = "//*[@id='#']"; 
        // Replacing above mentioned place holder with actual ID.
        divBtnXpath = divBtnXpath.replace("#", divId);
        Thread.sleep(1000);
        // Selecting the Division button and clicking it.
        WebElement divsionSelector = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(divBtnXpath)));
        Thread.sleep(2000);
        divsionSelector.click();
        Thread.sleep(1000);
        // Clicking the 'contest-ranks' button to go to the contest ranks.
        WebElement contestRanksBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='contest-ranks']/a")));
        contestRanksBtn.click();
        Thread.sleep(1000);
        // Getting the current windows list from chrome and switching the window to new opened window(ranks-list)
        ArrayList<String> wid = new ArrayList<String>(driv.getWindowHandles());
        driv.switchTo().window(wid.get(1));
        Thread.sleep(2000);
        // Selecting the filte button and clicking it 
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        WebElement filterByBtn = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[3]/div/div/div[2]/div[1]/div/div/div[2]/div[2]")));
        filterByBtn.click();
        Thread.sleep(1000);
        // Now selecting the "Institute" option fron the drop down menu and clicking it.
        WebElement institutionOption = driv.findElement(By.xpath("/html/body/div[3]/div[3]/ul/li[2]"));
        institutionOption.click();
        Thread.sleep(1000);
        // Selecting the text box for filter options and entering the deesired text.
        WebElement filterInputTextArea = driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[1]/div/div/div[3]/div/div/div/input")));
        filterInputTextArea.sendKeys("CMR Institute of Technology, Hyderabad");
        Thread.sleep(3000);
        // Using Action class create a mouse motion and click on the option that is displayed
        // after desired text in entered into the filter text box. We offset the mouse location to properly 
        // land on the desired location.
        act.moveToElement(filterInputTextArea,5,40).build().perform();
        Thread.sleep(1000);
        act.click().build().perform();
        Thread.sleep(1000);
        // Select and click on the Apply button to apply the filters.
        WebElement filterApplyBtn = driv.findElement(By.xpath("//*[@id='root']/div/div[3]/div/div/div[2]/div[1]/div/div/div[4]/button"));
        // In some cases the desired filter is not applciable for a Division so the Apply button will remain 
        // Disabled, we check this and make our move accordingly.
        if(!filterApplyBtn.isEnabled()){
            System.out.println("The following div does not have data:"+this.divName);
            this.interrupt();
        }
        filterApplyBtn.click();
        Thread.sleep(5000);
        // DATA READING PART.
        // Find the next-page-button that is used to go to the next page of results/rankings.1
        WebElement nextPagebtn = driv.findElement(By.className("_next-pagination__item_ehs9q_89"));
        // Create and open a file with the specific file name.
        String filePath = "output-files\\raw_html_"+divName+"_scores.txt";
        BufferedWriter contentsFile = new BufferedWriter(new FileWriter(filePath,true));
        // In some cases the ranks might only be one page, soo we check this and proceed accordingly.
        // If the button is not enabled, we will skip the statments in the if block below.
        if (nextPagebtn.isEnabled()){
            // Get the pages list in the bottom of the page. Convert it into a String and use split 
            // to get all page numbers.
            WebElement pagesList = driv.findElement(By.className("MuiPagination-ul"));
            String temp[] = pagesList.getText().split("\n");
            int max = 0;
            // Find the maximum pages in the list.
            for (int i = 0; i < temp.length; i++) {
                try{ 
                    int t = Integer.parseInt(temp[i]); 
                    if(t>max)
                    max = t;
                }
                catch(Exception e){}
                
            }
            Thread.sleep(3000);
            // If there is more than one page do the following.
            if(max>1){
                // The while loop exits when all pages are visited.
                while(max > 1){
                    // Get the list of ranks in that page and write it in a file.
                    WebElement ranksList = driv.findElement(By.className("MuiTableBody-root"));
                    contentsFile.write(ranksList.getAttribute("innerHTML"));
                    Thread.sleep(1000);
                    // Find and click on the next page button to move to the next page.
                    driverWait.until(ExpectedConditions.elementToBeClickable(By.className("_next-pagination__item_ehs9q_89")));
                    WebElement nextPagebtnRef = driv.findElement(By.className("_next-pagination__item_ehs9q_89"));
                    nextPagebtnRef.click();
                    Thread.sleep(5000);
                    max-=1;
                }
            }
        }
        // If the next-page button is disabled or there is only one page. Perform the following actions.
        // Get the raw HTML of ranks and write it to a file.
        WebElement ranksList = driv.findElement(By.className("MuiTableBody-root"));
        contentsFile.write(ranksList.getAttribute("innerHTML"));
        // Close the file and print the action in console.
        contentsFile.close();
        System.out.println("------------ Finished loading raw HTML to raw_html_"+divName+"_scores.txt ------------");
        driv.quit(); // Close the browser.
    }
    public static void main(String[] args) throws Exception{
        // Link to be visited
        String link = "https://www.codechef.com/START103";

        // Create four different Threads for each Division in CodeChef.

        // ScoreScrapper t1 = new ScoreScrapper("div-1",link);
        ScoreScrapper t2 = new ScoreScrapper("div-2",link);
        ScoreScrapper t3 = new ScoreScrapper("div-3",link);
        ScoreScrapper t4 = new ScoreScrapper("div-4",link);
        // Start each Thread by a one second delay.

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
