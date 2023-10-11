import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

public class Testing {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "score-scrapper\\assets\\chromedriver.exe");
        WebDriver driv = new ChromeDriver();
        driv.manage().window().maximize();
        driv.get("https://www.codechef.com/rankings/START99D?filterBy=Institution%3DCMR%20Institute%20of%20Technology%2C%20Hyderabad&itemsPerPage=100&order=asc&page=1&sortBy=rank");
        Thread.sleep(8000);
        
        WebElement pagesList = driv.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div/div[2]/div[2]/div/div[3]/div[3]/div/table/tfoot/tr/td/div[1]/nav/ul"));
        String temp[] = pagesList.getText().split("\n");
        int max = 0;
        for (int i = 0; i < temp.length; i++) {
            int t = Integer.parseInt(temp[i]);
            if(t>max)
                max = t;
        }
        System.out.println(max);
        while(max-- > 0){
            
        }
    }
}
